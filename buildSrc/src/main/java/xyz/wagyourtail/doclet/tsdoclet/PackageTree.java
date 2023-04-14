package xyz.wagyourtail.doclet.tsdoclet;

import xyz.wagyourtail.StringHelpers;
import xyz.wagyourtail.doclet.tsdoclet.parsers.ClassParser;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import java.util.*;

public class PackageTree {
    public final static Set<String> predefinedClasses = Set.of(
        "java.util.Collection", "java.util.List", "java.util.Map", "java.util.Set",
        "java.io.File", "java.net.URL", "java.net.URI", "java.lang.Object", "java.lang.Class",
        "java.lang.Throwable", "java.io.Serializable", "java.lang.StackTraceElement",
        "java.lang.Iterable"
    );
    // List of reserved keywords #2536
    // https://github.com/microsoft/TypeScript/issues/2536
    public final static Set<String> tsReservedWords = Set.of(
        "break", "case", "catch", "class", "const", "continue", "debugger", "default",
        "delete", "do", "else", "enum", "export", "extends", "false", "finally", "for",
        "function", "if", "import", "in", "instanceof", "new", "null", "return", "super",
        "switch", "this", "throw", "true", "try", "typeof", "var", "void", "while", "with"
    );
    private String pkgName;
    private Map<String, PackageTree> children = new LinkedHashMap<>();
    private Set<ClassParser> classes = new LinkedHashSet<>();
    private Map<ClassParser, String> compiledClasses = new LinkedHashMap<>();

    public boolean dirty = true;

    public PackageTree(String pkgName) {
        this.pkgName = pkgName;
    }

    public void addClass(Element clazz) {
        Stack<String> enclosing = new Stack<>();
        Element enclose = clazz;

        while (enclose != null && enclose.getKind() != ElementKind.PACKAGE) enclose = enclose.getEnclosingElement();

        if (enclose != null) {
            String[] pkg = ((PackageElement)enclose).getQualifiedName().toString().split("\\.");
            for (int i = pkg.length - 1; i >= 0; --i) {
                if (pkg[i].equals("")) continue;
                enclosing.push(pkg[i]);
            }
            if (predefinedClasses.contains(String.join(".", pkg) + "." + clazz.getSimpleName())) return;
        }
        addClassInternal(enclosing, clazz);
    }

    private boolean addClassInternal(Stack<String> enclosing, Element clazz) {
        if (enclosing.empty()) {
            this.dirty = classes.add(new ClassParser((TypeElement) clazz)) || this.dirty;
        } else {
            this.dirty = children.computeIfAbsent(enclosing.pop(), PackageTree::new)
                .addClassInternal(enclosing, clazz) || this.dirty;
        }
        return this.dirty;
    }

    private void prepareTSTree() {
        while (this.dirty) {
            this.dirty = false;
            for (ClassParser aClass : Set.copyOf(classes)) {
                compiledClasses.computeIfAbsent(aClass, ClassParser::genTSInterface);
            }
            for (PackageTree value : Set.copyOf(children.values())) {
                value.prepareTSTree();
            }
        }
    }

    public String genTSTree() {
        prepareTSTree();
        return genTSTreeIntern().replaceAll("\\bPackages\\.", "");
    }

    private String genTSTreeIntern() {
        if (classes.size() == 0 && children.size() == 1) {
            PackageTree onlyChild = children.values().stream().findFirst().get();
            if (!tsReservedWords.contains(onlyChild.pkgName)) {
                onlyChild.pkgName = pkgName + "." + onlyChild.pkgName;
                return onlyChild.genTSTreeIntern();
            }
        }

        StringBuilder exports = new StringBuilder("");
        StringBuilder s = new StringBuilder("namespace ");
        if (tsReservedWords.contains(pkgName)) {
            System.out.println("Escaped typescript reserved word: " + pkgName + " -> _" + pkgName);
            s.append("_");
        }
        s.append(pkgName).append(" {");

        for (ClassParser key : compiledClasses.keySet()) {
            if (exports.length() > 0) exports.append(",\n");
            exports.append(key.getClassName(false));
            s.append("\n\n").append(StringHelpers.tabIn(compiledClasses.get(key)));
        }
        boolean escaped = false;
        for (PackageTree value : children.values()) {
            if (exports.length() > 0) exports.append(",\n");
            if (tsReservedWords.contains(value.pkgName)) {
                exports.append("_").append(value.pkgName).append(" as ");
                escaped = true;
            }
            exports.append(value.pkgName);
            s.append("\n\n").append(StringHelpers.tabIn(value.genTSTreeIntern()));
        }

        if (escaped || compiledClasses.size() > 0) {
            if (exports.length() < 64) {
                s.append("\n\n    export { ").append(exports.toString().replaceAll("\n", " ")).append(" };");
            } else {
                s.append("\n\n    export {\n")
                    .append(StringHelpers.tabIn(exports.toString(), 2))
                .append("\n    };");
            }
        }
        s.append("\n\n}");

        return s.toString();
    }

    public List<ClassParser> getXyzClasses() {
        for (PackageTree value : children.values()) {
            if (value.pkgName.equals("xyz")) return value.getAllClasses();
        }
        return null;
    }

    public List<ClassParser> getAllClasses() {
        List<ClassParser> result = new ArrayList<>(classes);
        for (PackageTree value : children.values()) {
            result.addAll(value.getAllClasses());
        }
        return result;
    }
}
