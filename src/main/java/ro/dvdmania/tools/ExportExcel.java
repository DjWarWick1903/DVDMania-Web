package ro.dvdmania.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookFactory;

import ro.dvdmania.entities.Album;
import ro.dvdmania.entities.Game;
import ro.dvdmania.entities.Movie;
import ro.dvdmania.entities.Order;
import ro.dvdmania.entities.Stock;
import ro.dvdmania.entities.Store;

public class ExportExcel {

	private static ExportExcel instance = null;

	public static ExportExcel getInstance() {
		if (instance == null) {
			instance = new ExportExcel();
		}
		return instance;
	}

	public void writeStockToExcel(final ArrayList<Stock> stocks, final String city) {
		try {
			final Date date = new Date();

			// Create Blank workbook
			final XSSFWorkbook workbook = XSSFWorkbookFactory.createWorkbook(null);

			// Create a blank sheet
			final XSSFSheet spreadsheet;
			if (city != null) {
				spreadsheet = workbook.createSheet("Stock " + city + " " + date.getDate() + "." + date.getMonth() + "." + (date.getYear() + 1900));
			} else {
				spreadsheet = workbook.createSheet("Stock " + date.getDate() + "." + date.getMonth() + "." + (date.getYear() + 1900));
			}

			// Create row object
			XSSFRow row;

			// create data that needs to be written
			final Map<String, Object[]> info = new TreeMap<String, Object[]>();
			info.put("1", new Object[] { "ID", "Film", "Joc", "Album", "Magazin", "Cantitate", "Pret" });
			int i = 2;
			
			for (final Stock stock : stocks) {
				Movie movie = null;
				Game game = null;
				Album album = null;
				Store store = null;
				
				if (stock.getMovie() != null) {
					movie = stock.getMovie();
				}
				
				if (stock.getGame() != null) {
					game = stock.getGame();
				}
				
				if (stock.getAlbum() != null) {
					album = stock.getAlbum();
				}
				
				if (stock.getStore() != null) {
					store = stock.getStore();
				}

				final String movieStr = (movie == null) ? "" : movie.getTitle();
				final String gameStr = (game == null) ? "" : game.getTitle();
				final String albumStr = (album == null) ? "" : album.getArtist() + " - " + album.getTitle();
				final String storeStr = (store == null) ? "" : store.getOras() + " " + store.getAdresa();
				
				info.put(i + "", new Object[] { stock.getIdProduct() + "", movieStr, gameStr, albumStr, storeStr, stock.getQuantity() + "", stock.getPrice() + "" });
				i++;
			}

			// iterate data and write it to sheet
			final Set<String> keyId = info.keySet();
			int rowId = 0;

			for (final String key : keyId) {
				row = spreadsheet.createRow(rowId++);
				final Object[] objectArr = info.get(key);
				int cellid = 0;

				for (final Object obj : objectArr) {
					final Cell cell = row.createCell(cellid++);
					cell.setCellValue((String) obj);
				}
			}

			// Create file system using specific name
			FileOutputStream out = null;
			if (city != null) {
				out = new FileOutputStream(new File("Stock " + city + " " + date.getDate() + "." + date.getMonth() + "." + (date.getYear() + 1900) + ".xlsx"));
			} else {
				out = new FileOutputStream(new File("Stock " + date.getDate() + "." + date.getMonth() + "." + (date.getYear() + 1900) + ".xlsx"));
			}
			
			workbook.write(out);
			System.out.println("Created Stock " + date.getDate() + "." + date.getMonth() + "." + (date.getYear() + 1900) + ".xlsx successfully");
			
			if (out != null) {
				out.close();
			}
		} catch(final IOException e) {
			e.printStackTrace();
		}
	}

	public void writeOrdersToExcel(final ArrayList<Order> orders) {
		try {
			final Date date = new Date();

			// Create Blank workbook
			final XSSFWorkbook workbook = XSSFWorkbookFactory.createWorkbook(null);

			// Create a blank sheet
			final XSSFSheet spreadsheet = workbook.createSheet("Orders " + date.getDate() + "." + date.getMonth() + "." + (date.getYear() + 1900));

			// Create row object
			XSSFRow row;

			// create data that needs to be written
			final Map<String, Object[]> info = new TreeMap<String, Object[]>();
			info.put("1", new Object[] { "Produs", "Client", "Adresa client", "Angajat", "Magazin", "Data imprumutului","Data returului", "Pret" });
			int i = 2;
			
			for (final Order order : orders) {
				final Stock stock = order.getStock();
				Store store = null;
				String prodStr = null;
				
				if (stock.getMovie() != null) {
					final Movie movie = stock.getMovie();
					prodStr = movie.getTitle();
				}
				
				if (stock.getGame() != null) {
					final Game game = stock.getGame();
					prodStr = game.getTitle();
				}
				
				if (stock.getAlbum() != null) {
					final Album album = stock.getAlbum();
					prodStr = album.getArtist() + " - " + album.getTitle();
				}
				
				if (stock.getStore() != null) {
					store = stock.getStore();
				}

				final String storeStr = (store == null) ? "" : store.getOras() + " " + store.getAdresa();
				final String clientStr = order.getClient().getNume() + " " + order.getClient().getPrenume();
				final String clientAdrStr = order.getClient().getAdresa();
				final String empStr = order.getEmployee().getNume() + " " + order.getEmployee().getPrenume();
				final String orderDate = order.getBorrowDate().toString();
				
				String retDate = null;
				if (order.getReturnDate() != null) {
					retDate = order.getReturnDate().toString();
				} else {
					retDate = "";
				}
				
				info.put(i + "", new Object[] { prodStr, clientStr, clientAdrStr, empStr, storeStr, orderDate, retDate,order.getPrice() + "" });
				i++;
			}

			// iterate data and write it to sheet
			final Set<String> keyId = info.keySet();
			int rowId = 0;

			for (final String key : keyId) {
				row = spreadsheet.createRow(rowId++);
				final Object[] objectArr = info.get(key);
				int cellid = 0;

				for (final Object obj : objectArr) {
					final Cell cell = row.createCell(cellid++);
					cell.setCellValue((String) obj);
				}
			}

			// Create file system using specific name
			FileOutputStream out = null;
			out = new FileOutputStream(new File("Orders " + date.getDate() + "." + date.getMonth() + "." + (date.getYear() + 1900) + ".xlsx"));
			workbook.write(out);
			System.out.println("Created Orders " + date.getDate() + "." + date.getMonth() + "."+ (date.getYear() + 1900) + ".xlsx successfully");
			
			if (out != null) {
				out.close();
			}
		} catch(final IOException e) {
			e.printStackTrace();
		}
	}
}
