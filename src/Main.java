
public class Main {
    public static void main(String[] args) {
        try {
            CertificateAuthority.generateKeyPair();
            User user1 = new User();
            User user2 = new User();
            user1.requestACertificate();
            System.out.println(user2.verifyACertificate() ? "Certificate is valid!" : "Certificate is invalid!");
            Thread.sleep(10000);
            System.out.println(user2.verifyACertificate() ? "Certificate is valid!" : "Certificate is invalid!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
