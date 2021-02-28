package com.sqills.assignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@ApplicationScoped
public class TextConsumer implements Runnable {

    @Inject
    ConnectionFactory connectionFactory;
    
    @Override
    public void run() {
        try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
            JMSConsumer consumer = context.createConsumer(context.createQueue("text"));
            while (true) {
                Message message = consumer.receive();
                if (message == null) {
                    // receive returns `null` if the JMSConsumer is closed
                    return;
                }
                
                String text = message.getBody(String.class);
                
                //1 Tokenize the input using space as delimiter
                List<String> step1 = step1Process(text);
                
                //2 replace each non alphanumeric character
                List<String> step2 = step2Process(step1);
                
                //Convert all tokens to uppercase
                List<String> step3 = step3Process(step2);
                
                String stringToLog = toString(step1) + toString(step2) + toString(step3);
				System.out.println(stringToLog);

            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

	public List<String> step1Process(String text) {
		String[] ste1Array = text.split("\\s+");
		List<String> step1 = Arrays.asList(ste1Array);
		return step1;
	}
	
	public List<String> step2Process(List<String> step1) {
		List<String> step2 = new ArrayList<String>();
		for(String str:step1) {
		    step2.add(str.replaceAll("[^A-Za-z0-9]", "_"));
		}
		return step2;
	}

    public List<String> step3Process(List<String> step2) {
		List<String> step3 = new ArrayList<String>();
		for(String str:step2) {
		    step3.add(str.toUpperCase());
		}
		return step3;
	}

	public String toString(List<String> stringList) {
        String resultString = "";
		for(String str:stringList) {
			resultString += (str + " ");
        }
		return resultString;
	}
}
