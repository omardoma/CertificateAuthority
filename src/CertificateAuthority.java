import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

public class CertificateAuthority {
    static PublicKey publicKey;
    private static PrivateKey privateKey;

    public static void generateKeyPair() throws Exception {
        KeyPair keyPair = Utils.generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }

    public static void generateCertificate(PublicKey userKey) throws Exception {
        Certificate certificate = new Certificate(userKey);
        certificate.getAttributes().add(Base64.getEncoder().encodeToString(signCertificate(certificate)));
        writeFile(User.CERT_FILE_NAME, certificate);
    }

    private static byte[] signCertificate(Certificate certificate) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey);
        StringBuilder sb = new StringBuilder();
        for (String attr : certificate.getAttributes()) {
            sb.append(attr);
        }
        privateSignature.update(sb.toString().getBytes());
        return privateSignature.sign();
    }

    private static void writeFile(String fileName, Certificate certificate) throws IOException {
        Files.write(Paths.get(fileName), certificate.getAttributes());
    }
}
