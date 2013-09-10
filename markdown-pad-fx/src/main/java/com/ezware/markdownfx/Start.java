package com.ezware.markdownfx;

import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.controlsfx.control.action.ActionGroup;
import org.controlsfx.control.action.ActionMap;
import static org.controlsfx.control.action.ActionMap.action;
import static org.controlsfx.control.action.ActionMap.actions;
import org.controlsfx.control.action.ActionProxy;
import org.controlsfx.control.action.ActionUtils;
import org.controlsfx.control.action.ActionUtils.ActionTextBehavior;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

public class Start extends javafx.application.Application implements DocumentEditorProvider {

    private TabPane tabs = new TabPane();
    private Stage stage;

    public Start() {
    	ActionMap.register(this);
	}
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override 
    public void start(Stage primaryStage) {
        
    	stage = primaryStage;
    	
        createDocumentEditor();
        
        MenuBar menuBar = ActionUtils.createMenuBar( Arrays.asList( 
              new ActionGroup("File", actions("newdoc", "opendoc", "savedoc", "---", "appexit")),
              new ActionGroup("Edit", actions("cut", "opendoc", "savedoc"))       
        ));
        
        menuBar.setUseSystemMenuBar(true); // Mac OSX support
        
        
        ToolBar toolBar = ActionUtils.createToolBar( Arrays.asList(
        	action("newdoc"), action("opendoc"), action("savedoc"),
            ActionUtils.ACTION_SEPARATOR,
            action("cut"), action("copy"), action("paste")
        ), ActionTextBehavior.HIDE);
        
        BorderPane content = new BorderPane();
        content.setTop(new VBox(menuBar, toolBar));
        content.setCenter(tabs);
        content.setPrefSize(1000, 800);
//        Pane statusBar = new Pane( new Label("v 1.0.0"));
//        statusBar.setPadding(new Insets(5,5,5,5));
//        content.setBottom(statusBar);
        
        primaryStage.setTitle("Markdown Pad FX");
        primaryStage.setScene(new Scene(content));
        primaryStage.show();
    }
    
//    private Action action(String id) {
//    	return ActionMap.get(id);
//    }
    
    @ActionProxy(id="newdoc", text="New", image="/images/file2.png", longText = "Create New Document")
    private DocumentEditor createDocumentEditor() {
        final DocumentEditor editor = new DocumentEditor("");
        Tab tab = new Tab( "New Document" );
        tab.setContent(editor);
        tab.setOnCloseRequest(new EventHandler<Event>() {
            
            public void handle(Event e) {
                
               Dialogs dlg = Dialogs.create().title("Confirmation").message("Close tab?");
               if ( editor.isDirty() && dlg.showConfirm() != Dialog.Actions.YES ){
                  e.consume();
               }
                
            }
        });
        
        tabs.getTabs().add(tab);
        tabs.getSelectionModel().select(tab);
        return editor;
    }
    
    @ActionProxy( id ="appexit", text="Exit")
    private void appExit(ActionEvent e) {
    	if ( stage != null ) stage.close();
    } 
    
    
    @ActionProxy( id ="opendoc", text="Open", image="/images/folder-open.png")
    private void openDocument(ActionEvent e) {
    	System.out.println("Start.openDocument()");
    } 
    
    @ActionProxy( id ="savedoc", text="Save", image="/images/disk.png")
    private void saveDocument(ActionEvent e) {
    	System.out.println("Start.saveDocument()");
    } 
    
    @ActionProxy( id ="cut", text="Cut", image="/images/scissors.png")
    private void cut(ActionEvent e) {
    	System.out.println("Start.cut()");
    }
    
    @ActionProxy( id ="copy", text="Cut", image="/images/copy.png")
    private void copy(ActionEvent e) {
    	System.out.println("Start.copy()");
    }
    
    @ActionProxy( id ="paste", text="Paste", image="/images/paste2.png")
    private void paste(ActionEvent e) {
    	System.out.println("Start.paste()");
    }


    public DocumentEditor getDocumentEditor() {
        Tab tab = tabs.getSelectionModel().getSelectedItem();
        return  tab == null? null: (DocumentEditor) tab.getContent();
    }

}


