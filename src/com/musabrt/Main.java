package com.musabrt;
import com.musabrt.async.Threader;

import javax.swing.UIManager;

public class Main {

	public static void main(String[] args) {
		decorateWindow();
		new CreateFrame(new Threader());
	}


	private static void decorateWindow() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
    }
}