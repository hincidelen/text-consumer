package com.sqills.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.sqills.assignment.TextConsumer;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class TextConsumerTest {

	private TextConsumer textConsumer;
    
	List<String> step1expectedResult = Arrays.asList("com.SQILLS.assignment", "an.ot8er", "Sample.1nput-Str");
    List<String> step2expectedResult = Arrays.asList("com_SQILLS_assignment", "an_oth8r", "Sample_1nput_Str");
    List<String> step3expectedResult = Arrays.asList("COM_SQILLS_ASSIGNMENT", "AN_OTH8R", "SAMPLE_1NPUT_STR");
	String toStringExpected = "com.SQILLS.assignment an.ot8er Sample.1nput-Str com_SQILLS_assignment an_oth8r Sample_1nput_Str COM_SQILLS_ASSIGNMENT AN_OTH8R SAMPLE_1NPUT_STR";

	@Test
	void testStep1Process() {

		String text = "com.SQILLS.assignment an.ot8er  Sample.1nput-Str";
		List<String> step1List = textConsumer.step1Process(text);
		assertEquals(step1expectedResult, step1List);
	}

	@Test
	void testStep2Process() {
		List<String> step2List = textConsumer.step2Process(step1expectedResult);
		assertEquals(step2expectedResult, step2List);	
	}

	@Test
	void testStep3Process() {
		List<String> step3List = textConsumer.step3Process(step2expectedResult);
		assertEquals(step3expectedResult, step3List);	
	}

	@Test
	void testToStringListOfString() {
        String stringToLog = textConsumer.toString(step1expectedResult) + textConsumer.toString(step2expectedResult) + textConsumer.toString(step3expectedResult);
		assertEquals(toStringExpected, stringToLog);
	}

}
