import java.security.PublicKey;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;

public class Certificate {
    private Timestamp timestamp;
    private ArrayList<String> attributes;

    public Certificate(PublicKey publicKey) {
        timestamp = new Timestamp(System.currentTimeMillis() + 10000);
        attributes = new ArrayList<>();
        attributes.add(timestamp.toString());
        attributes.add(Base64.getEncoder().encodeToString(publicKey.getEncoded()));
    }

    public ArrayList<String> getAttributes() {
        return attributes;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
