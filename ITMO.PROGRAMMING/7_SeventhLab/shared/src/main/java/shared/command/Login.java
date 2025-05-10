package shared.command;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import shared.CommandHandler;
import shared.AuthHandler;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
@XmlRootElement
public class Login extends UsableCommand implements RemoteCommand, LoginCommand{


    public Login(String username, String password, CommandHandler invoker) {
        super("Login", "Команда для авторизации уже зарегестрированного пользователя");
        setUsername(username);
        this.setInvoker(invoker);
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD2");
        } catch (NoSuchAlgorithmException e) {}
        md.update(password.getBytes());
        byte[] digest = md.digest();
        BigInteger no = new BigInteger(1, digest);

        String hashtext = no.toString(16);

        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        setPassword(hashtext);
    }
    public Login() {
        this("", "", null);
    }
}
