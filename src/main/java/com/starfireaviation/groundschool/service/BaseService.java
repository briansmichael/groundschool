package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.config.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

@Slf4j
public abstract class BaseService {

    /**
     * SIXTEEN.
     */
    public static final int SIXTEEN = 16;

    /**
     * Secret Key.
     */
    protected PaddedBufferedBlockCipher cipher;

    @Autowired
    private ApplicationProperties applicationProperties;

    protected Connection getSQLLiteConnection(final String course) {
        Connection sqlLiteConn = null;
        try {
            String sqlLiteJDBCUrl = "jdbc:sqlite:" + applicationProperties.getDbSrcLocation() + course + ".db";
            DriverManager.registerDriver(new org.sqlite.JDBC());
            sqlLiteConn = DriverManager.getConnection(sqlLiteJDBCUrl);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return sqlLiteConn;
    }

    /**
     * Decrypts provided encrypted value.
     *
     * @param encryptedDataStr encrypted data
     * @return decrypted value
     */
    public String decrypt(final String encryptedDataStr) throws InvalidCipherTextException {
        byte[] out2 = Base64.decode(encryptedDataStr);
        byte[] comparisonBytes = new byte[cipher.getOutputSize(out2.length)];
        int length = cipher.processBytes(out2, 0, out2.length, comparisonBytes, 0);
        cipher.doFinal(comparisonBytes, length);
        return new String(comparisonBytes).trim();
    }

    public void initCipher(final String secretKey, final String initVector) {
        if (Objects.isNull(cipher)) {
            AESEngine engine = new AESEngine();
            CBCBlockCipher blockCipher = new CBCBlockCipher(engine);
            cipher = new PaddedBufferedBlockCipher(blockCipher);
            KeyParameter keyParam = new KeyParameter(Base64.decode(secretKey));
            cipher.init(false, new ParametersWithIV(keyParam, initVector.getBytes(StandardCharsets.UTF_8), 0, SIXTEEN));
        }
    }

}
