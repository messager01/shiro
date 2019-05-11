package cn.wolfcodde;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;

import java.sql.PseudoColumnUsage;

public class MD5Test {

    @Test
    public void testMd5(){
        String password = "666";     // 明文密码
        // 加密
        Md5Hash md5Hash  = new Md5Hash(password);
        System.out.println(md5Hash);       // fae0b27c451c728867a567e8c1bb4e53

        // md5 + 盐
        Md5Hash md5Hash1 = new Md5Hash(password, "shipeixin");
        System.out.println(md5Hash1);         // f8b48e5457a5a91873b34fca63b888d0

        //  md5 + 盐 + 散列次数              //  fd8b787a126c2da852b1087281151dfc
        Md5Hash md5Hash2 = new Md5Hash(password, "shipeixin", 3);
        System.out.println(md5Hash2);
    }
}
