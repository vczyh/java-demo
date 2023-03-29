import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SslProvider;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class RedisTest {
    /**
     * import ca.crt to truststore:
     * keytool -import -alias ca -file ca.crt -keystore /tmp/truststore
     */


    @Test
    public void testRedission() throws MalformedURLException {
//        System.setProperty("")


        // 1. Create config object
        Config config = new Config();

        config.useSingleServer()
//                .setAddress("100.100.1.99:6379")
//                .setAddress("rediss://100.100.6.105:6379")
                .setAddress("rediss://dbaas:6379")
                .setUsername("default")
                .setPassword("Zggyy2019!")
//                .setPassword("zKhBcdKOfJ5fVycR8oq4muSJbzQyIFOc");
                .setSslEnableEndpointIdentification(false)
                .setSslProvider(SslProvider.JDK)
                .setSslProtocols(new String[]{"TLSv1.2"})
                .setSslTruststore(new URL("file:///tmp/truststore"))
                .setSslTruststorePassword("123456");

//        config.useClusterServers()
        // use "rediss://" for SSL connection
//                .addNodeAddress("redis://127.0.0.1:7181");

        // Sync and Async API
        RedissonClient client = Redisson.create(config);

        RBucket<String> b = client.getBucket("a");
        System.out.println(b.get());
    }

    @Test
    public void testLettuceTLS() {
        SslOptions sslOptions = SslOptions.builder()
                .jdkSslProvider()
                .truststore(new File("/tmp/truststore"), "123456")
                .build();

        ClientOptions clientOptions = ClientOptions.builder().sslOptions(sslOptions).build();


        RedisURI redisUri = RedisURI.Builder.redis("100.100.5.169")
                .withSsl(true)
                .withPassword("Zggyy2019!")
                .build();
//        redisUri.setVerifyPeer(false);
        redisUri.setVerifyPeer(SslVerifyMode.CA);
        RedisClient client = RedisClient.create(redisUri);
        client.setOptions(clientOptions);

        StatefulRedisConnection<String, String> connect = client.connect();
        RedisAsyncCommands<String, String> async = connect.async();
        RedisFuture<String> result = async.set("k", "v");
        connect.flushCommands();
        System.out.println(result);
    }
}
