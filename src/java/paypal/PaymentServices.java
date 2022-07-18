/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paypal;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author GraphX
 */
public class PaymentServices {
    private static final String CLIENT_ID = "Client";
    private static final String CLIENT_SECRET = "Client Secret";
    private static final String MODE = "sandbox";
 
    public String authorizePayment(OrderDetail orderDetail)        
            throws PayPalRESTException {       
 
        Payer payer = getPayerInformation();
        RedirectUrls redirectUrls = getRedirectURLs();
             List<Transaction> listTransaction = getTransactionInformation(orderDetail);
        Payment requestPayment = new Payment();
        requestPayment.setTransactions(listTransaction);
        requestPayment.setRedirectUrls(redirectUrls);
        requestPayment.setPayer(payer);
        requestPayment.setIntent("authorize");
 
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
 
        Payment approvedPayment = requestPayment.create(apiContext);
 
        return getApprovalLink(approvedPayment);
 
    }
     
    private Payer getPayerInformation() {
        Payer payer = new Payer();
    payer.setPaymentMethod("paypal");
     
    PayerInfo payerInfo = new PayerInfo();
    payerInfo.setFirstName("William")
             .setLastName("Peterson")
             .setEmail("william.peterson@company.com");
     
    payer.setPayerInfo(payerInfo);
     
    return payer;
    }
     
    private RedirectUrls getRedirectURLs() {
           RedirectUrls redirectUrls = new RedirectUrls();
    redirectUrls.setCancelUrl("http://localhost:43943/JWP_Projekt/checkout.jsp");
    redirectUrls.setReturnUrl("http://localhost:43943/JWP_Projekt/checkout.jsp");
     
    return redirectUrls;
    }
     
    private List<Transaction> getTransactionInformation(OrderDetail orderDetail) {
    Details details = new Details();
    details.setShipping(orderDetail.getShipping());
    details.setSubtotal(orderDetail.getSubtotal());
    details.setTax(orderDetail.getTax());
 
    Amount amount = new Amount();
    amount.setCurrency("USD");
    amount.setTotal(orderDetail.getTotal());
    amount.setDetails(details);
 
    Transaction transaction = new Transaction();
    transaction.setAmount(amount);
    transaction.setDescription(orderDetail.getProductName());
     
    ItemList itemList = new ItemList();
    List<Item> items = new ArrayList<>();
     
    Item item = new Item();
    item.setCurrency("USD");
    item.setName(orderDetail.getProductName());
    item.setPrice(orderDetail.getSubtotal());
    item.setTax(orderDetail.getTax());
    item.setQuantity("1");
     
    items.add(item);
    itemList.setItems(items);
    transaction.setItemList(itemList);
 
    List<Transaction> listTransaction = new ArrayList<>();
    listTransaction.add(transaction);  
     
    return listTransaction;
        
    }
     
    private String getApprovalLink(Payment approvedPayment) {
        List<Links> links = approvedPayment.getLinks();
    String approvalLink = null;
     
    for (Links link : links) {
        if (link.getRel().equalsIgnoreCase("approval_url")) {
            approvalLink = link.getHref();
            break;
        }
    }      
     
    return approvalLink;
    }
}