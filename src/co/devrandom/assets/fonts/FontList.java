package co.devrandom.assets.fonts;

import org.newdawn.slick.TrueTypeFont;

public enum FontList {
	BODY("iceland.ttf", 14),
	HEADER("iceland.ttf", 22),
	TITLE("iceland.ttf", 48);
	
	private TrueTypeFont font;
	
	private FontList(String fileName, float size) {
		this.font = FontLoader.loadTTF(fileName, size);
	}
	
	public TrueTypeFont getFont() {
		return font;
	}
	
	public static void initFonts() {
		for (FontList font : FontList.values()){
			font.getFont();
		}
	}
}

