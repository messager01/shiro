package cn.wolfcodde;


/*
*
* 测试shiro验证
* */

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class ShiroTest {


    @Test
    public  void testPermission(){
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-permission.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "666");
        subject.login(token);
        // 判断当前用户是否拥有某一个权限    返回true或false
        System.out.println(subject.isPermitted("user:delete"));

        //判断用户是否拥有一些权限     返回true 表示都拥有
        System.out.println(subject.isPermittedAll("user:create","user:delete","user:update"));

        //  以数组的形式 返回是否拥有某些权限
        System.out.println(Arrays.toString(subject.isPermitted("user:list","user:create")));

        //  如果拥有指定权限  没有返回值
        subject.checkPermission("user:create");

        String permission = "list";
        //  如果不拥有指定权限  报异常
        try {
            subject.checkPermission("user:"+permission);
        } catch (AuthorizationException e) {
            System.out.println("当前用户不具备"+permission+"权限");
        }


    }

    @Test
    public void testHasRoles(){
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-permission.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "666");
        subject.login(token);

        //进行授权的前提是必须成功登陆

        // 判断当前用户是否拥有某个角色
        System.out.println("zhangsan是否拥有role1？"+"  "+subject.hasRole("role1"));
        //  判断是否拥有一些角色     false  表示不全部拥有
        System.out.println("zhangsan是否拥有role1,role2?"+ " "+subject.hasAllRoles(Arrays.asList("role1","role2","role3")));
        //  将是否拥有权限的结果  以数组表示出来
        System.out.println(Arrays.toString(subject.hasRoles(Arrays.asList("role1","role2","role3"))));

        System.out.println("---------------------------------------------------------------------------");
        //   以下的API 也是判断当前用户是否拥有某个角色  但是没有返回值
        subject.checkRole("role1");       //  如果拥有该角色  没有返回值
        //    如果不拥有该角色  抛出异常  UnauthorizedException: Subject does not have role [role3]
        //subject.checkRole("role3");

        //判断当前用户是否拥有一些角色
        subject.checkRoles("role1","role2");   //  只要有一个角色不被拥有  就会抛出异常


    }





    @Test
    public void testLoGIN(){
        //  1.构建secruitymanager  工厂对象
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //  2.通过工厂  创建SerurityManager 对象
        SecurityManager securityManager = factory.getInstance();
        //  3. 将securityManager  绑定到当前运行环境   让系统随时随刻都可访问SecurityManager这个对象
        SecurityUtils.setSecurityManager(securityManager);
        //  4.创建当前登录的主体
        Subject subject = SecurityUtils.getSubject();    //  注意  此时的主题并没有经过认证

        //  5.主体登录                                        //  登录用户名        // 登录密码
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "6668");
        try {
            subject.login(token);
        }catch (Exception e){
            System.out.println("登陆失败");
        }

        //  6. 验证登录是否成功
        System.out.println("验证是否 登录成功"+subject.isAuthenticated());
        //  注销
        subject.logout();
        System.out.println("验证是否 登录成功"+subject.isAuthenticated());
    }






    @Test
    public void testLoginByMyRealm(){
        //  1.构建secruitymanager  工厂对象
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-realm.ini");
        //  2.通过工厂  创建SerurityManager 对象
        SecurityManager securityManager = factory.getInstance();
        //  3. 将securityManager  绑定到当前运行环境   让系统随时随刻都可访问SecurityManager这个对象
        SecurityUtils.setSecurityManager(securityManager);
        //  4.创建当前登录的主体
        Subject subject = SecurityUtils.getSubject();    //  注意  此时的主题并没有经过认证

        //  5.主体登录                                        //  登录用户名        // 登录密码
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "6668");
        try {
            subject.login(token);
        }catch (Exception e){
            e.printStackTrace();
        }

        //  6. 验证登录是否成功
        System.out.println("验证是否 登录成功"+subject.isAuthenticated());
        //  注销
        subject.logout();
        System.out.println("验证是否 登录成功"+subject.isAuthenticated());
    }







    @Test
    public void testMd5(){
        //  1.构建secruitymanager  工厂对象
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-cryptography.ini");
        //  2.通过工厂  创建SerurityManager 对象
        SecurityManager securityManager = factory.getInstance();
        //  3. 将securityManager  绑定到当前运行环境   让系统随时随刻都可访问SecurityManager这个对象
        SecurityUtils.setSecurityManager(securityManager);
        //  4.创建当前登录的主体
        Subject subject = SecurityUtils.getSubject();    //  注意  此时的主题并没有经过认证

        //  5.主体登录                                        //  登录用户名        // 登录密码

        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan8", "6668");
        try {
            subject.login(token);
        }catch (Exception e){
            e.printStackTrace();
        }

        //  6. 验证登录是否成功
        System.out.println("验证是否 登录成功"+subject.isAuthenticated());
        //  注销
        subject.logout();
        System.out.println("验证是否 登录成功"+subject.isAuthenticated());
    }
}
