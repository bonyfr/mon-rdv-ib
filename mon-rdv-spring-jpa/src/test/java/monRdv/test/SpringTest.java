package monRdv.test;

import javax.sql.DataSource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import monRdv.config.ApplicationConfig;

public class SpringTest {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
		
//		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		
		DataSource ds = context.getBean(DataSource.class);
		
				
		
		
		context.close();
	}
}
