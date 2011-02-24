package com.google.gwt.sample.stockwatcher.client;

import com.google.gwt.sample.stockwatcher.shared.FieldVerifier;
//下面两句是添加监听鼠标事件的
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
//下面三句是添加监听键盘输入框事件的进口报关单
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
//
import com.google.gwt.user.client.Window;
//
import java.util.ArrayList;
//
import com.google.gwt.user.client.Timer;
//写refreshWatchList（）方法时用到
import com.google.gwt.user.client.Random;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 入口处类定义
 */
public class StockWatcher implements EntryPoint {
	
	  private static final int REFRESH_INTERVAL = 5000; // 制定刷新率，以毫秒位单位
	  private VerticalPanel mainPanel = new VerticalPanel(); //垂直面板
	  private FlexTable stocksFlexTable = new FlexTable();//FlexTable部件
	  private HorizontalPanel addPanel = new HorizontalPanel();
	  private TextBox newSymbolTextBox = new TextBox();//文本框部件
	  private Button addStockButton = new Button("Add");//按钮部件
	  private Label lastUpdatedLabel = new Label();
	  //一个新的数据结构用表的方式来保存用户输入的股票代码
      private ArrayList<String> stocks = new ArrayList<String>();
	  /**
	   * Entry point method.入口点方法
	   */
	  public void onModuleLoad() {
	    //  Create table for stock data.一个表来保存数据的股票
		//Symbol这些就是这张表的标题，第一个参数表示行，第二个参数是表示表的第几列
		  stocksFlexTable.setText(0, 0, "Symbol");  
		  stocksFlexTable.setText(0, 1, "Price");
		  stocksFlexTable.setText(0, 2, "Change");
		  stocksFlexTable.setText(0, 3, "Remove");
	    //  组装添加股票面板
		  addPanel.add(newSymbolTextBox);
		  addPanel.add(addStockButton);
	    //  组装主要面板
		  mainPanel.add(stocksFlexTable);
		  mainPanel.add(addPanel);
		  mainPanel.add(lastUpdatedLabel);
		  
	    // 主面板
		  RootPanel.get("stockList").add(mainPanel);
	    // 移动光标焦点到输入框
		  newSymbolTextBox.setFocus(true);
		  
		  //设置定时自动刷新列表
		  Timer refreshTimer = new Timer() {
		      @Override
		      public void run() {
		        refreshWatchList();
		      }
		    };
		    refreshTimer.scheduleRepeating(REFRESH_INTERVAL);

		  
		//添加按钮上监听鼠标事件
		  addStockButton.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		        addStock();
		      }
		    });
		 //监听键盘输入框的事件。 
		  newSymbolTextBox.addKeyPressHandler(new KeyPressHandler() {
		      public void onKeyPress(KeyPressEvent event) {
		        if (event.getCharCode() == KeyCodes.KEY_ENTER) {
		          addStock();
		        }
		      }
		    });

		  }

		  /**
		   * 新增股票的 FlexTable. 当用户点击 按钮 或者按
		   *  enter键 时执行.
		   */
		  private void addStock() {
			   final String symbol = newSymbolTextBox.getText().toUpperCase().trim();
			    newSymbolTextBox.setFocus(true);

			    // 股票代码必须介于1和10个字符的数字、字母和点。
			    //如果不是，则显示对话框提示这不是有效的股票代码
			    if (!symbol.matches("^[0-9A-Z\\.]{1,10}$")) {
			      Window.alert("'" + symbol + "' is not a valid symbol.");
			      newSymbolTextBox.selectAll();
			      return;
			    }

			    newSymbolTextBox.setText("");

			     // 如果它已经在表中不要添加。 
			    if (stocks.contains(symbol))
			        return;
			     // 将刚输入的股票代码经判断后添加到表。 
			    int row = stocksFlexTable.getRowCount();
			    stocks.add(symbol);
			    stocksFlexTable.setText(row, 0, symbol);
			     //添加一个按钮，从表删除该股票。 
			    Button removeStockButton = new Button("x");
			    removeStockButton.addClickHandler(new ClickHandler() {
			      public void onClick(ClickEvent event) {
			        int removedIndex = stocks.indexOf(symbol);
			        stocks.remove(removedIndex);
			        stocksFlexTable.removeRow(removedIndex + 1);
			      }
			    });
			    stocksFlexTable.setWidget(row, 3, removeStockButton);
			     //获取股票价格。
			    refreshWatchList();

		  }
		  //产生随机的股票价格
		  private void refreshWatchList() {
			  final double MAX_PRICE = 100.0; // 最高价格是100
			    final double MAX_PRICE_CHANGE = 0.02; // 最大价格变动是0.02

			    StockPrice[] prices = new StockPrice[stocks.size()];//定义数组
			    for (int i = 0; i < stocks.size(); i++) {
			      double price = Random.nextDouble() * MAX_PRICE;
			      double change = price * MAX_PRICE_CHANGE
			          * (Random.nextDouble() * 2.0 - 1.0);

			      prices[i] = new StockPrice(stocks.get(i), price, change);
			    }

			    updateTable(prices);
			  }
		  private void updateTable(StockPrice[] prices) {
			    // TODO Auto-generated method stub

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


