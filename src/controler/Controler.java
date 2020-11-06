/**
 * 
 */
package controler;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

import model.AudioPlayer;
import model.RecordTimer;
import model.Recorder;
import model.SpeechText;
import model.TextSpeech;
import model.Translator;
import view.Window;

/**
 * @author Eliantus De MICHEL
 *
 */
public class Controler implements ActionListener {
	
	private Window w;
	private RecordTimer timer;
	private Recorder recorder;
	private AudioPlayer player, reader;
	private TextSpeech tts;
	private String lastlgsent;
	private SpeechText stt;
	private Translator translator;
	
	//Thread
	private Thread playbackThread;
	private Thread sendThread;
	private Thread recordThread;
	private Thread tradThread;
	private Thread readBackThread;

	
	//Program Status
	private boolean isRecording = false;
	private boolean isPlaying = false;
	private boolean hasSent=false;
	private boolean isSending=false;
	private boolean isReading=false;
	private boolean isTranslating=false;
	
	private String inPath;
	private String dirPath=".SpeechTotext";
	private String outPath=dirPath+"/"+".audio.wav";
	private String word;

	public Controler(Window win) {
		w = win;
		recorder = new Recorder();
		player = new AudioPlayer();
		reader = new AudioPlayer();
		
		//Decorate button
		w.Torecord();
		w.Toplay();
		w.Tosend();
		w.Totranslate(w.list2().getSelectedItem().toString());
		w.Toread();
		
		//Disable button
		w.enButton(w.record(), false, true);
		
		//Add action listener on Window button
		w.record().addActionListener(this);
		w.play().addActionListener(this);
		w.send().addActionListener(this);
		w.translate().addActionListener(this);
		w.read().addActionListener(this);
		
		w.list1().addActionListener(this);
		w.list2().addActionListener(this);
		
		try {
			Files.createDirectories(Paths.get(dirPath));
			Files.setAttribute(Paths.get(dirPath), "dos:hidden", Boolean.TRUE, LinkOption.NOFOLLOW_LINKS);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//Action sur Recording button
		if(e.getSource()== w.record()) {
			if (!isRecording) {
				startRecording();
			} else {
				stopRecording();
			}
		}
		
		//Action sur Playing Button
		if (e.getSource()== w.play()) {
			if (!isPlaying) {
				playBack(inPath);
			} else {
				stopPlaying();
			}
		}
		
		//Action for send
		if(e.getSource()==w.send()) {
			if(!isSending) {
				
				isSending=true;
				boolean internet= testNet();
				
				stt = new SpeechText();
				
				if(!(hasSent && w.list1().getSelectedItem().toString().equals(lastlgsent) && internet)) {
					
					if(w.list1().getSelectedItem().toString()=="French" && internet) {
						
						sendThread = new Thread(new Runnable() {
							@Override
							public void run() {
								w.Tostop(w.send(),"Stop sending","");
								w.time().setText(" Time: 00:00:00 ");
								w.enButton(w.send(), false, false);
								w.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
								w.ecran1().setText(stt.ToTextFr(inPath));
								w.enButton(w.send(), true, true);
								hasSent=true;
								lastlgsent=w.list1().getSelectedItem().toString();
								w.Tosend();
								w.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
								if(w.ecran1().getText().length()==0) {
									JOptionPane.showMessageDialog(null,
											"No sound has been detected!\nPlease record the paragraph again", "Warning",
											JOptionPane.WARNING_MESSAGE);
									
									hasSent=false;
								}
								isSending=false;
							}
						});
						sendThread.start();
						
						
					}
					if(w.list1().getSelectedItem().toString()=="English" && internet) {
						
						sendThread = new Thread(new Runnable() {
							@Override
							public void run() {
								w.Tostop(w.send(),"Stop sending","");
								w.time().setText(" Time: 00:00:00 ");
								w.enButton(w.send(), false, false);
								w.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
								w.ecran1().setText(stt.ToTextEn(inPath));
								w.enButton(w.send(), true, true);
								hasSent=true;
								lastlgsent=w.list1().getSelectedItem().toString();
								w.Tosend();
								w.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
								if(w.ecran1().getText().length()==0) {
									JOptionPane.showMessageDialog(null,
											"No sound has been detected!\nPlease record the paragraph again", "Warning",
											JOptionPane.WARNING_MESSAGE);
									
									hasSent=false;
								}
								isSending=false;
							}
						});
						sendThread.start();												
					}
					
				}
				else {
					isSending=false;
				}
				
			}
			
			else {
				isSending=false;
				stt.stop();
				sendThread.interrupt();
				w.Tosend();
				w.ecran1().setText("");
				hasSent=false;
				w.enButton(w.send(), true, true);
				w.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
								
			}
				
		}
		
		// Action Translate
		if(e.getSource()==w.translate()) {
			
			if(!isTranslating) {
				
				isTranslating=true;
				
				if(hasSent) {
					
					boolean internet = testNet();
					translator = new Translator();
					word = w.ecran1().getText().substring(0,w.ecran1().getText().length()-2);
					
					if(w.list2().getSelectedItem().toString()=="French" && internet) {
						
						tradThread = new Thread(new Runnable() {
							@Override
							public void run() {
								w.Tostop(w.translate(), "Stop translating", " _Stop_    ");
								w.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
								w.enButton(w.translate(), false, false);
								translator.translate(word, "en-fr");
								String word1 = translator.Parse();
								w.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
								w.ecran2().setText(word1);
								w.enButton(w.translate(), true, true);
								w.Totranslate(w.list2().getSelectedItem().toString());
								isTranslating=false;
							}
						});
						tradThread.start();	
					}
					
					if(w.list2().getSelectedItem().toString()=="English" && internet) {
						
						tradThread = new Thread(new Runnable() {
							@Override
							public void run() {
								w.Tostop(w.translate(), "Stop translating", " _Stop_    ");
								w.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
								w.enButton(w.translate(), false, false);
								translator.translate(word, "fr-en");
								String word1 = translator.Parse();
								w.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
								w.ecran2().setText(word1);
								w.enButton(w.translate(), true, true);
								w.Totranslate(w.list2().getSelectedItem().toString());
								isTranslating=false;
							}
						});
						tradThread.start();	
					}
					
				}
				else {
					JOptionPane.showMessageDialog(null,
							"Sorry! There is noting to translate!",
							"Empty Error", JOptionPane.ERROR_MESSAGE);
					isTranslating=false;
				}
				
			
			}
			else {
				isTranslating=false;
				translator.stop();
				tradThread.interrupt();
				w.Totranslate(w.list2().getSelectedItem().toString());
				w.ecran2().setText("");
				w.enButton(w.translate(), true, true);
				w.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				
			}
		}
		
		
		
		//Action sur List1
		if (e.getSource()== w.list1()) {
			
			if(w.list1().getSelectedItem().toString()=="French") {
				w.list2().setSelectedItem("English");
			}
			else {
				w.list2().setSelectedItem("French");
			}
			
		}
		
		//Action sur List2
		if (e.getSource()== w.list2()) {
			w.Totranslate(w.list2().getSelectedItem().toString());
			
			if(w.list2().getSelectedItem().toString()=="French") {
				w.list1().setSelectedItem("English");
				
			}
			else {
				w.list1().setSelectedItem("French");
			}
			
		}
		
		if (e.getSource()==w.translate()) {
			
		}
		
		
		
		// Action read*********************
		if(e.getSource()==w.read()) {
			
			if(w.ecran2().getText().length()==0) {
				JOptionPane.showMessageDialog(null,
						"Sorry! There is noting to Read!",
						"Empty Error", JOptionPane.ERROR_MESSAGE);
				isReading=false;
			}
			
			else {
				tts = new TextSpeech();
				
				if(!isReading) {
					
					isReading=true;
					boolean internet=testNet();
					
					if(internet) 
						readBack();
									
				}
				
				else {
					System.out.println("Cancel");
					stopReading();
					isReading=false;
					w.Toread();
					w.enButton(w.read(), true, true);
					w.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
										
				}
			}
		}
		
	}
	
	
	
	private void startRecording() {
		hasSent=false;
		recordThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					isRecording = true;
					w.ecran1().setText(null);
					w.ecran2().setText(null);
					w.enButton(w.record(), false, false);
					w.Tostop(w.record(),"Stop recording","");
					recorder.start();

				} catch (LineUnavailableException ex) {
					JOptionPane.showMessageDialog(null,
							"Error", "Could not start recording sound!",
							JOptionPane.ERROR_MESSAGE);
					//ex.printStackTrace();
				}
			}
		});
		recordThread.start();
		timer = new RecordTimer(w.time());
		timer.start();
	}
	
	private void stopRecording() {
		isRecording = false;
		try {
			timer.cancel();
			w.Torecord();
			w.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			recorder.stop();
			saveFile();
			w.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Error",
					"Error stopping sound recording!",
					JOptionPane.ERROR_MESSAGE);
			//ex.printStackTrace();
		}
	}
	
	private void saveFile() {
			inPath= dirPath+"/"+".temp.wav";

			File wavFile = new File(inPath);

			try {
				recorder.save(wavFile);
				w.enButton(w.record(), true, true);
				
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(null, "Error",
						"Error saving to sound file!",
						JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
	}
	
	private void readBack() {
		timer = new RecordTimer(w.time());
		timer.start();
		isReading = true;
		readBackThread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {

					w.Tostop(w.read(),"Stop Reading","Stop reading");
					w.enButton(w.read(), false, false);
					w.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					
					if(w.list2().getSelectedItem().toString()=="French")
						tts.TextSpeechFr(w.ecran2().getText(), outPath);
					else
						tts.TextSpeechEn(w.ecran2().getText(), outPath);
					
					reader.play(outPath);
					timer.reset();
					w.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					w.Toread();
					w.enButton(w.read(), true, true);
					isReading = false;

				} catch (UnsupportedAudioFileException ex) {
					ex.printStackTrace();
				} catch (LineUnavailableException ex) {
					ex.printStackTrace();
				} catch (IOException ex) {
					ex.printStackTrace();
				}

			}
		});

		readBackThread.start();
	}
		
	
	private void stopReading() {
		timer.reset();
		timer.interrupt();
		reader.stop();
		readBackThread.interrupt();
	}
	

	
	private void playBack(String path) {
		timer = new RecordTimer(w.time());
		timer.start();
		isPlaying = true;
		playbackThread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {

					w.Tostop(w.play(),"Stop playing","");
					w.enButton(w.play(), false, true);

					player.play(path);
					timer.reset();

					w.Toplay();
					w.enButton(w.play(), true, true);
					isPlaying = false;

				} catch (UnsupportedAudioFileException ex) {
					ex.printStackTrace();
				} catch (LineUnavailableException ex) {
					ex.printStackTrace();
				} catch (IOException ex) {
					ex.printStackTrace();
				}

			}
		});

		playbackThread.start();
	}

	/**
	 * Stop playing back.
	 */
	private void stopPlaying() {
		timer.reset();
		timer.interrupt();
		player.stop();
		playbackThread.interrupt();
	}
	
	public boolean testNet() {
		
		boolean internet = false;
		
		if(isSending) {
			w.Tostop(w.send(), "Stop Checking", "");
			w.enButton(w.send(), false, false);
		}
		
		if(isTranslating) {
			w.Tostop(w.translate(), "Stop Cheking", " _Stop_    ");
			w.enButton(w.translate(), false, false);
		}
			
		if(isReading) {
			w.Tostop(w.read(), "Stop Cheking", " _Stop_ ");
			w.enButton(w.read(), true, true);
		}
			
		
		try {
	         URL url = new URL("http://www.google.com");
	         URLConnection connection = url.openConnection();
	         connection.connect();
	         internet = true;
	         w.enButton(w.read(), true, true); w.Tosend(); w.Totranslate(w.list2().getSelectedItem().toString()); w.Toread();
		} catch (IOException a) {
			internet = false;
			JOptionPane.showMessageDialog(null,
					"Sorry! You can't use Speech to Text API now.\nPlease, check your internet connection and try again!",
					"Network Error", JOptionPane.ERROR_MESSAGE);
			w.Tosend(); w.Totranslate(w.list2().getSelectedItem().toString()); w.Toread(); w.enButton(w.send(), true, true);
	      }
		return internet;
	}
	
		
	
	public Window getWin() {
		return w;
	}


}
