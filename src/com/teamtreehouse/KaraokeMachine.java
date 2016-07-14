package com.teamtreehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.teamtreehouse.model.Song;
import com.teamtreehouse.model.SongBook;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class KaraokeMachine{
	private SongBook mSongBook;
	private BufferedReader mReader;
	private Map<String,String> mMenu;


	public KaraokeMachine( SongBook songBook) {
		mSongBook = songBook;
		mReader = new BufferedReader(new InputStreamReader(System.in));
		mMenu = new HashMap<String, String>();
		mMenu.put("add", "Add a new song to the SongBook");
		mMenu.put("choose", "Choose a song to sing");
		mMenu.put("quit", "Give up. Exit the program");

	}

	private String promptAction() throws IOException {
		System.out.printf("There are %d songs availabel. Your options ar : %n",
				mSongBook.getSongCount());
		for(Map.Entry<String, String> option : mMenu.entrySet()) {
			System.out.printf("%s - %s %n",
					option.getKey(),
					option.getValue());
		}
		System.out.print("What do you want to do: ");
		String choice = mReader.readLine();
		return choice.trim().toLowerCase();
	}
	public void run(){
		String choice = "";
		do {
			try {
				choice = promptAction();
				switch (choice) {
					case "add":
						Song song = promptNewSong();
						mSongBook.addSong(song);
						System.out.printf("%s added!  %n%n", song);
						break;
					case "choose":
						String artist = promptArtist();
						Song artistSong = promptSongForArtist(artist);
						// TODO: 14/07/16 Add to a song queue
						System.out.printf("You choose: %s %n", artistSong);
						break;
					case "quit":
						System.out.println("Thanks for playing");
						break;
					default:
						System.out.printf("Unknown choice: %s. Try again . %n%n",
								choice);
				}
			} catch (IOException ioe) {
				System.out.println("Problem with input");
				ioe.printStackTrace();
			}
		} while (!choice.equals("quit"));
	}
	private Song promptNewSong() throws IOException {
		System.out.print("Enter the artist's name: ");
		String artist = mReader.readLine();
		System.out.print("Enter the title: ");
		String title = mReader.readLine();
		System.out.print("Enter the video URL: ");
		String videoUrl = mReader.readLine();
		return  new Song(artist, title,videoUrl);
	}

	private String promptArtist() throws IOException{
		System.out.println("Available artists:");
		List<String> artists = new ArrayList<>((mSongBook.getArtists()));
		int index = promptForIndex(artists);
		return artists.get(index);
	}

	private Song promptSongForArtist(String artist) throws IOException{
		List<Song> songs = mSongBook.getSongsForArtist(artist);
		List<String> songTitles = new ArrayList<>();
		for(Song song :  songs) {
			songTitles.add(song.getTitle());
		}
		int index = promptForIndex(songTitles);
		return  songs.get(index);
	}

	private int promptForIndex(List<String> options) throws IOException {
		int counter = 1;
		for( String option : options) {
			System.out.printf("%d.) %s %n", counter, option);
			counter++;
		}
		String optionAsString = mReader.readLine();
		int choice = Integer.parseInt(optionAsString.trim());
		System.out.print("Your choice: ");
		return choice -1;
	}
}