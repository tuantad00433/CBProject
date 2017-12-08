import com.fasterxml.jackson.databind.ObjectMapper;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TestConnect {
    public void connectTest() throws Exception{
        Web3j web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/4ANomGN5hGuPbpCZEIoj"));
        Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
        System.out.println(web3ClientVersion.getWeb3ClientVersion());

        ObjectMapper obj = new ObjectMapper();
        WalletFile walletFile = obj.readValue("{\"address\":\"1a1d4c9a06409e6c8d03951b4c2e1771fe3df489\",\"id\":\"d40bf842-4740-46a8-88b6-e2a5df5bf3fd\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"279bfbe0c7b6891bf58eba0ebd0ab36ee8ac9cc84c1dcc15791cce9368a27cb4\",\"cipherparams\":{\"iv\":\"4067be6fb4f031db0965ec17569f73ad\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":5,\"p\":10,\"r\":8,\"salt\":\"96ab00eea0f9669b4efdcfa4ed0aa3dec57683a592bfb1504d0d66ed5ac7b3a3\"},\"mac\":\"ffbcf9ea496be59651d9c71cfe1ffdb7f3cb7b7a401a8986895ad52a3d2a974f\"}}",WalletFile.class);
        Credentials credentials = Credentials.create(Wallet.decrypt("abcd",walletFile));
        EthGetBalance ethGetBalance = web3j.ethGetBalance("0xB279182D99E65703F0076E4812653aaB85FCA0f0", DefaultBlockParameterName.LATEST).sendAsync().get();
        BigInteger wei = ethGetBalance.getBalance();
        System.out.println(wei);

        TransactionReceipt transactionReceipt = Transfer.sendFunds(web3j,credentials,"0xB279182D99E65703F0076E4812653aaB85FCA0f0", BigDecimal.valueOf(0), Convert.Unit.ETHER).send();

    }

    public static void main(String[] args) throws  Exception {
//        new TestConnect().connectTest();
    }
}
