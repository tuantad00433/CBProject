import com.entity.Address;
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
import static com.googlecode.objectify.ObjectifyService.ofy;

public class TestConnect {
    public void connectTest() throws Exception{
        Web3j web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/4ANomGN5hGuPbpCZEIoj"));
//        Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
//        System.out.println(web3ClientVersion.getWeb3ClientVersion());

        ObjectMapper obj = new ObjectMapper();
//        WalletFile walletFile = obj.readValue("{\"address\":\"802d2d518738803d1e0adb5e990dcbf158e42574\",\"id\":\"ea54d7de-ceb2-4e6e-902c-b5e960afe984\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"d96420ddcd364a7400b013297cb552986ebfa8333ba5bbef03a3878e18155a53\",\"cipherparams\":{\"iv\":\"b5072a94724afac015342e912049154b\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":5,\"p\":10,\"r\":8,\"salt\":\"8a0c9e2046cef454fd60e0e65d4d107dedbc75de5808b2fe1e37c9c9599e833d\"},\"mac\":\"e38236064a7e30679a9c13f4accffb92bb718d8a3373b7e20e80f24934304393\"}}",WalletFile.class);
//        Credentials credentials = Credentials.create(Wallet.decrypt("45d3020c-7995-4e68-9654-2b7b3b274ba7",walletFile));
        EthGetBalance ethGetBalance = web3j.ethGetBalance("0x802d2d518738803d1e0adb5e990dcbf158e42574", DefaultBlockParameterName.LATEST).sendAsync().get();
        BigInteger wei = ethGetBalance.getBalance();
        double amount = wei.doubleValue();
        System.out.println(amount);
        System.out.println(wei);

//        TransactionReceipt transactionReceipt = Transfer.sendFunds(web3j,credentials,"0x8ed1fd5a58fc3eaf3a0c5f1fa0335c68c96ea2e4", BigDecimal.valueOf(0.001), Convert.Unit.ETHER).send();
//        EthGetBalance ethGetBalance = web3j.ethGetBalance("0x8ed1fd5a58fc3eaf3a0c5f1fa0335c68c96ea2e4", DefaultBlockParameterName.LATEST).sendAsync().get();
//        System.out.println(ethGetBalance.getBalance());
    }


    public static void main(String[] args) throws  Exception {
        new TestConnect().connectTest();
    }
}
