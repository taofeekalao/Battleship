import java.io.File;

class Siblings {

    public static boolean areSiblings(File f1, File f2) {
        // implement me
        System.out.println(f1.getParent());
        System.out.println(f2.getParent());
        return f1.isFile() && f2.isFile() && (f1.getParent() == f2.getParent());
    }

}
