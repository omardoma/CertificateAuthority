import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.List;

public class User {
    private PublicKey publicKey;
    private PrivateKey privateKey;
    public static final String CERT_FILE_NAME = "cert";

    public User() throws Exception {
        generateKeyPair();
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    private void generateKeyPair() throws Exception {
        KeyPair keyPair = Utils.generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }

    public void requestACertificate() throws Exception {
        CertificateAuthority.generateCertificate(publicKey);
    }

    public boolean verifyACertificate() throws Exception {
        List<String> certificateAttributes = readCertificate();
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(CertificateAuthority.publicKey);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < certificateAttributes.size() - 1; i++) {
            sb.append(certificateAttributes.get(i));
        }
        publicSignature.update(sb.toString().getBytes());
        return !new Timestamp(System.currentTimeMillis()).after(Utils.stringToTimestamp(certificateAttributes.get(0))) && publicSignature.verify(Base64.getDecoder().decode(certificateAttributes.get(certificateAttributes.size() - 1)));
    }

    private List<String> readCertificate() throws IOException {
        return Files.readAllLines(Paths.get(CERT_FILE_NAME));
    }
}
