package petstore;

public class Paths {

    public static String Pet = "/pet";
    public static String FindByStatus = Pet + "/findByStatus";
    public static String FindByTags = Pet + "/findByTags";
    public static String ById(String id) {
        return Pet + "/" + id;
    }

    public static String UploadImage(String id) {
        return Pet + "/" + id + "/" + "uploadImage";
    }
}
