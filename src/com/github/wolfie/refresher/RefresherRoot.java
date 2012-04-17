package com.github.wolfie.refresher;

import com.github.wolfie.refresher.Refresher.RefreshListener;
import com.vaadin.terminal.WrappedRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.Root;

public class RefresherRoot extends Root {
	
	public class DatabaseListener implements RefreshListener {
		private static final long serialVersionUID = -8765221895426102605L;
		
		@Override
		public void refresh(final Refresher source) {
			if (databaseResult != null) {
				// stop polling
				source.setEnabled(false);
				
				// replace the "loading" with the actual fetched result
				content.setValue("Database result was: " + databaseResult);
			}
		}
	}
	
	public class DatabaseQueryProcess extends Thread {
		@Override
		public void run() {
			databaseResult = veryHugeDatabaseCalculation();
		}
		
		private String veryHugeDatabaseCalculation() {
			// faux long lasting database query
			try {
				Thread.sleep(6000);
			} catch (final InterruptedException ignore) {
				return "interrupted!";
			}
			return "huge!";
		}
	}
	
	private static final long serialVersionUID = -1744455941100836080L;
	
	private String databaseResult = null;
	private Label content;
	
	@Override
	public void init(final WrappedRequest request) {
		// present with a loading contents.
		content = new Label("please wait while the database is queried");
		addComponent(content);
		
		// the Refresher polls automatically
		final Refresher refresher = new Refresher();
		refresher.addListener(new DatabaseListener());
		addComponent(refresher);
		
		new DatabaseQueryProcess().start();
	}
}
