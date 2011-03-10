package com.google.gwt.sample.stockwatcher.client;

import com.google.gwt.sample.stockwatcher.shared.FieldVerifier;
//������������Ӽ�������¼���
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.RootPanel;
//������������Ӽ�������������¼��Ľ��ڱ��ص�
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
//
import com.google.gwt.user.client.Window;
//
import java.util.ArrayList;
//
import com.google.gwt.user.client.Timer;
//дrefreshWatchList��������ʱ�õ�
import com.google.gwt.user.client.Random;
//ʵ�ַ���updateTable(StockPrice).ʱ�õ�
import com.google.gwt.i18n.client.NumberFormat;
//���ʱ�����ʱ���õ�
import com.google.gwt.i18n.client.DateTimeFormat;
import java.util.Date;



/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * ��ڴ��ඨ��
 */
public class StockWatcher implements EntryPoint {
	
	  private static final int REFRESH_INTERVAL = 5000; // �ƶ�ˢ���ʣ��Ժ���λ��λ
	  private VerticalPanel mainPanel = new VerticalPanel(); //��ֱ���
	  private FlexTable stocksFlexTable = new FlexTable();//FlexTable����
	  private HorizontalPanel addPanel = new HorizontalPanel();
	  private TextBox newSymbolTextBox = new TextBox();//�ı��򲿼�
	  private Button addStockButton = new Button("Add");//��ť����
	  private Label lastUpdatedLabel = new Label();
	  //һ���µ����ݽṹ�ñ�ķ�ʽ�������û�����Ĺ�Ʊ����
      private ArrayList<String> stocks = new ArrayList<String>();
	  /**
	   * Entry point method.��ڵ㷽��
	   */
	  public void onModuleLoad() {
	    //  Create table for stock data.һ�������������ݵĹ�Ʊ
		//Symbol��Щ�������ű�ı��⣬��һ��������ʾ�У��ڶ��������Ǳ�ʾ��ĵڼ���
		  stocksFlexTable.setText(0, 0, "Symbol");  
		  stocksFlexTable.setText(0, 1, "Price");
		  stocksFlexTable.setText(0, 2, "Change");
		  stocksFlexTable.setText(0, 3, "Remove");
		  stocksFlexTable.setCellPadding(6);
		  
		  // ��ӱ���ʽ�б��е�Ԫ�صĹ�Ʊ
		    stocksFlexTable.getRowFormatter().addStyleName(0, "watchListHeader");
		    //��StockWatcher.css��������watchList������Ҫ���������������������Լ����������
		    stocksFlexTable.addStyleName("watchList");
		    /*���ù�Ʊ�б��Price��Change���е����Ҷ���ʱ��
		    ��StockWatcher.css��������watchListNumericColumn��ʽ����,���������*/
		    stocksFlexTable.getCellFormatter().addStyleName(0, 1, "watchListNumericColumn");
		    stocksFlexTable.getCellFormatter().addStyleName(0, 2, "watchListNumericColumn");
		    //���ð�ť���У��Ѱ�ť����ʱ����StockWatcher.css��������watchListNumericColumn��ʽ����,���������
		    stocksFlexTable.getCellFormatter().addStyleName(0, 3, "watchListRemoveColumn");
		    
	    //  ��װ��ӹ�Ʊ���
		  addPanel.add(newSymbolTextBox);
		  addPanel.add(addStockButton);
		  /*����������ӹ�Ʊ�������Χ�ı�Ե����StockWatcher.css��������addPanel��ʽ�������������*/
		  addPanel.addStyleName("addPanel");
	    //  ��װ��Ҫ���
		  mainPanel.add(stocksFlexTable);
		  mainPanel.add(addPanel);
		  mainPanel.add(lastUpdatedLabel);
		  
	    // �����
		  RootPanel.get("stockList").add(mainPanel);
	    // �ƶ���꽹�㵽�����
		  newSymbolTextBox.setFocus(true);
		  
		  //���ö�ʱ�Զ�ˢ���б�
		  Timer refreshTimer = new Timer() {
		      @Override
		      public void run() {
		        refreshWatchList();
		      }
		    };
		    refreshTimer.scheduleRepeating(REFRESH_INTERVAL);

		  
		//��Ӱ�ť�ϼ�������¼�
		  addStockButton.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		        addStock();
		      }
		    });
		 //���������������¼��� 
		  newSymbolTextBox.addKeyPressHandler(new KeyPressHandler() {
		      public void onKeyPress(KeyPressEvent event) {
		        if (event.getCharCode() == KeyCodes.KEY_ENTER) {
		          addStock();
		        }
		      }
		    });

		  }

		  /**
		   * ������Ʊ�� FlexTable. ���û���� ��ť ���߰�
		   *  enter�� ʱִ��.
		   */
		  private void addStock() {
			   final String symbol = newSymbolTextBox.getText().toUpperCase().trim();
			    newSymbolTextBox.setFocus(true);

			    // ��Ʊ����������1��10���ַ������֡���ĸ�͵㡣
			    //������ǣ�����ʾ�Ի�����ʾ�ⲻ����Ч�Ĺ�Ʊ����
			    if (!symbol.matches("^[0-9A-Z\\.]{1,10}$")) {
			      Window.alert("'" + symbol + "' is not a valid symbol.");
			      newSymbolTextBox.selectAll();
			      return;
			    }

			    newSymbolTextBox.setText("");

			     // ������Ѿ��ڱ��в�Ҫ��ӡ� 
			    if (stocks.contains(symbol))
			        return;
			     // ��������Ĺ�Ʊ���뾭�жϺ���ӵ��� 
			    int row = stocksFlexTable.getRowCount();
			    stocks.add(symbol);
			    stocksFlexTable.setText(row, 0, symbol);
			    //
			    stocksFlexTable.setWidget(row, 2, new Label());
			    //��onModuleLoad()�������������е���ʽ�����ڸ�������ͬ������ʽ
			    stocksFlexTable.getCellFormatter().addStyleName(row, 1, "watchListNumericColumn");
			    stocksFlexTable.getCellFormatter().addStyleName(row, 2, "watchListNumericColumn");
			    stocksFlexTable.getCellFormatter().addStyleName(row, 3, "watchListRemoveColumn");
			     //���һ����ť���ӱ�ɾ���ù�Ʊ�� 
			    Button removeStockButton = new Button("x");
			    //
			    removeStockButton.addStyleDependentName("remove");
			    removeStockButton.addClickHandler(new ClickHandler() {
			      public void onClick(ClickEvent event) {
			        int removedIndex = stocks.indexOf(symbol);
			        stocks.remove(removedIndex);
			        stocksFlexTable.removeRow(removedIndex + 1);
			      }
			    });
			    stocksFlexTable.setWidget(row, 3, removeStockButton);
			     //��ȡ��Ʊ�۸�
			    refreshWatchList();

		  }
		  //��������Ĺ�Ʊ�۸�
		  private void refreshWatchList() {
			  final double MAX_PRICE = 100.0; // ��߼۸���100
			    final double MAX_PRICE_CHANGE = 0.02; // ���۸�䶯��0.02

			    StockPrice[] prices = new StockPrice[stocks.size()];//��������
			    for (int i = 0; i < stocks.size(); i++) {
			      double price = Random.nextDouble() * MAX_PRICE;
			      double change = price * MAX_PRICE_CHANGE
			          * (Random.nextDouble() * 2.0 - 1.0);

			      prices[i] = new StockPrice(stocks.get(i), price, change);
			    }

			    updateTable(prices);
			  }
		  //ʵ�ָ÷���updateTable��StockPrices []�� 
		  private void updateTable(StockPrice[] prices) {  //����������
			   for (int i = 0; i < prices.length; i++) {
				      updateTable(prices[i]);//�տ�ʼ������������Ϊ����updateTable��û����
				    }
			// ���ʱ�������һ��Label�ؼ���lastUpdatedLabel�������û������е�ʱ����� 
			   //��������Ϊ��ǩ�ؼ����ı���
			    lastUpdatedLabel.setText("Last update : "
			        + DateTimeFormat.getMediumDateTimeFormat().format(new Date()));
			  }
		  //ʵ�ַ���updateTable(StockPrice),����һ�����е��еĹ�Ʊ����
		  private void updateTable(StockPrice price) {
			    // ȷ���ù�Ʊ����������ݴ���
			    if (!stocks.contains(price.getSymbol())) {
			      return;
			    }

			    int row = stocks.indexOf(price.getSymbol()) + 1;

			    // ʹPrice��Change���е����ݸ�ʽ����ͳһ���0.00�ĸ�ʽ������λС��
			    String priceText = NumberFormat.getFormat("#,##0.00").format(
			        price.getPrice());
			    NumberFormat changeFormat = NumberFormat.getFormat("+#,##0.00;-#,##0.00");
			    String changeText = changeFormat.format(price.getChange());
			    /*�����и�bug����changePercentText������ֵ�����������ط�price.getChangePercent,
			     * ����Change���еı仯�� ֻ��1/10����ȷֵ�Ĵ�С�������Ҫ���ԡ�
			     * ������ǵ��Եķ���
	             *��ˣ������ڸ�������һ���ϵ㣬Ȼ��������жϳ��ڼ���仯�İٷֱ��� 
                 */
			    String changePercentText = changeFormat.format(price.getChangePercent());

			    // ������������Price��Change ������.
			    stocksFlexTable.setText(row, 1, priceText);
			    //stocksFlexTable.setText(row, 2, changeText + " (" + changePercentText
			       // + "%)");   ԭ������Σ��ĳ��������
			    Label changeWidget = (Label)stocksFlexTable.getWidget(row, 2);
			    changeWidget.setText(changeText + " (" + changePercentText + "%)");
			    
			 // ����Change���е�������ɫ��������ɫ����������ֵ���仯��
			    String changeStyleName = "noChange";
			    if (price.getChangePercent() < -0.1f) {
			      changeStyleName = "negativeChange";
			    }
			    else if (price.getChangePercent() > 0.1f) {
			      changeStyleName = "positiveChange";
			    }

			    changeWidget.setStyleName(changeStyleName);
			  }
		  

	  }
	
	
	
	
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	/**
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	  /**private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	/**public void onModuleLoad() {
		final Button sendButton = new Button("Send");
		final TextBox nameField = new TextBox();
		nameField.setText("GWT User");
		final Label errorLabel = new Label();

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
	/**	public void onClick(ClickEvent event) {
				sendNameToServer();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
	/**	public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a response.
			 */
	/**	private void sendNameToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				String textToServer = nameField.getText();
				if (!FieldVerifier.isValidName(textToServer)) {
					errorLabel.setText("Please enter at least four characters");
					return;
				}

				// Then, we send the input to the server.
				sendButton.setEnabled(false);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");
				greetingService.greetServer(textToServer,
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								// Show the RPC error message to the user
								dialogBox
										.setText("Remote Procedure Call - Failure");
								serverResponseLabel
										.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(SERVER_ERROR);
								dialogBox.center();
								closeButton.setFocus(true);
							}

							public void onSuccess(String result) {
								dialogBox.setText("Remote Procedure Call");
								serverResponseLabel
										.removeStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(result);
								dialogBox.center();
								closeButton.setFocus(true);
							}
						});
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);
	}
	
}*/


