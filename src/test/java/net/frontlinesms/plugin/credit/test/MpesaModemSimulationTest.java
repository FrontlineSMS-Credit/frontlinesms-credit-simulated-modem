package net.frontlinesms.plugin.credit.test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.TooManyListenersException;

import net.frontlinesms.payment.PaymentServiceException;
import net.frontlinesms.payment.safaricom.MpesaPaymentService;
import net.frontlinesms.payment.safaricom.MpesaPersonalService;

import org.creditsms.plugins.paymentview.data.domain.Account;
import org.creditsms.plugins.paymentview.data.domain.Client;
import org.creditsms.plugins.paymentview.data.domain.OutgoingPayment;
import org.junit.Before;
import org.junit.Test;
import org.smslib.CService;
import org.smslib.DebugException;
import org.smslib.SMSLibDeviceException;

import serial.NoSuchPortException;
import serial.PortInUseException;
import serial.UnsupportedCommOperationException;

public class MpesaModemSimulationTest{
	private MpesaPaymentService mpesaPaymentService; 
	 
	@Before
	public void setUp(){
		try {
			CreditSimulationLauncher.setupMockModem();
		} catch (Exception e) {
			e.printStackTrace();
		}
		CService cService = new CService("COM1", 9600, "Wavecom", "Stk", null);
		if(!(cService.getAtHandlerName().equals("CATHandler_Wavecom_Stk"))) {
			throw new RuntimeException("Failed to initialise AT Handler as expected.");
		}
		
		try {
			cService.connect();
		} catch (PortInUseException e) {
			e.printStackTrace();
		} catch (NoSuchPortException e) {
			e.printStackTrace();
		} catch (UnsupportedCommOperationException e) {
			e.printStackTrace();
		} catch (SMSLibDeviceException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TooManyListenersException e) {
			e.printStackTrace();
		} catch (DebugException e) {
			e.printStackTrace();
		}
		
		mpesaPaymentService = new MpesaPersonalService();
		mpesaPaymentService.setCService(cService);
	}
	
	@Test
	public void testMakePayment (){
		try {
			Client client = new Client();
			client.setPhoneNumber("254701010101");
			mpesaPaymentService.makePayment(client, new OutgoingPayment(client, new BigDecimal("13433.32"), new Account(), ""));
//			mpesaPaymentService.makePayment(client, new OutgoingPayment(client, new BigDecimal("13433.32"), new Account(), ""));
		} catch (PaymentServiceException e) {
			e.printStackTrace();
		}
	}
}
