import java.util.ArrayList;
import java.util.Random;
/**
 * A class to hold details of audio tracks.
 * Individual tracks may be played.
 * 
 * @author Jeffrey Kolvites
 * @version 2024.10.07
 */
public class MusicOrganizer
{
    // An ArrayList for storing music tracks.
    private ArrayList<Track> tracks;
    // A player for the music tracks.
    private MusicPlayer player;
    // A reader that can read music files and load them as tracks.
    private TrackReader reader;

    /**
     * Create a MusicOrganizer
     */
    public MusicOrganizer()
    {
        tracks = new ArrayList<>();
        player = new MusicPlayer();
        reader = new TrackReader();
        readLibrary("../audio");
        System.out.println("Music library loaded. " + getNumberOfTracks() + " tracks.");
        System.out.println();
    }
    
    /**
     * Add a track file to the collection.
     * @param filename The file name of the track to be added.
     */
    public void addFile(String filename)
    {
        tracks.add(new Track(filename));
    }
    
    /**
     * Add a track to the collection.
     * @param track The track to be added.
     */
    public void addTrack(Track track)
    {
        tracks.add(track);
    }
    
    /**
     * Play a track in the collection.
     * @param index The index of the track to be played.
     */
    public void playTrack(int index)
    {
        if(indexValid(index)) {
            Track track = tracks.get(index);
            player.playSample(track.getFilename());
            System.out.println("Now playing: " + track.getArtist() + " - " + track.getTitle());
        }
    }
    
    /**
     * Return the number of tracks in the collection.
     * @return The number of tracks in the collection.
     */
    public int getNumberOfTracks()
    {
        return tracks.size();
    }
    
    /**
     * List a track from the collection.
     * @param index The index of the track to be listed.
     */
    public void listTrack(int index)
    {
        if(validIndex(index) == true)
        {
            System.out.print("Track " + index + ": ");
            Track track = tracks.get(index);
            System.out.println(track.getDetails());
        }
        else
        {
            System.out.println("Valid range must be between 0 and " + (tracks.size()-1));
        }
    }
    
    /**
     * Show a list of all the tracks in the collection.
     */
    public void listAllTracks()
    {
        System.out.println("Track listing: ");

        for(Track track : tracks) {
            System.out.println(track.getDetails());
        }
        System.out.println();
    }
    
    /**
     * List all tracks by the given artist.
     * @param artist The artist's name.
     */
    public void listByArtist(String artist)
    {
        for(Track track : tracks) {
            if(track.getArtist().contains(artist)) {
                System.out.println(track.getDetails());
            }
        }
    }
    
    /**
     * Remove a track from the collection.
     * @param index The index of the track to be removed.
     */
    public void removeTrack(int index)
    {
        if(validIndex(index) == true)
        {
            tracks.remove(index);
        }
        else
        {
            System.out.println("Valid range must be between 0 and " + (tracks.size()-1));
        }
    }
    
    /**
     * Play the first track in the collection, if there is one.
     */
    public void playFirst()
    {
        if(tracks.size() > 0) {
            player.startPlaying(tracks.get(0).getFilename());
        }
    }
    
    /**
     * Stop the player.
     */
    public void stopPlaying()
    {
        player.stop();
    }
    
    /**
     * validIndex 
     * @para
     */
    public boolean validIndex(int index)
    {
        if ((index >= 0) && (index <= tracks.size()-1))
        {
            return true;
        }
        else
        {
            return false; 
        }
    }

    /**
     * Play a random track from the collection.
     */
    public void shuffle()
    {
        Random shuffle = new Random();
        if(tracks.size() > 0) 
        {
            int randomTrack = shuffle.nextInt(tracks.size());
            Track trackPlaying = tracks.get(randomTrack);
            player.startPlaying(tracks.get((randomTrack)).getFilename());
            System.out.println("Now Playing: " + trackPlaying.getDetails());
        }
        else
        {
            System.out.println("Track list is empty");
        }
    }
    
    /**
     * Shuffles and plays all tracks in the collection.
     */
    public void shuffleAll()
    {
        if(tracks.size() > 0)
        {
            ArrayList<Track> randomTracks = new ArrayList<Track>(tracks);
            Random shuffle = new Random();
            while(randomTracks.size() > 0)
            {
                int randomTrack = shuffle.nextInt(randomTracks.size());
                Track trackPlaying = randomTracks.remove(randomTrack);
                System.out.println("Now Playing: " + trackPlaying.getDetails());
                player.playWhole(trackPlaying.getFilename());
            }
        }
         else
        {
            System.out.println("Track list is empty");
        }

    }
    
    /**
     * Determine whether the given index is valid for the collection.
     * Print an error message if it is not.
     * @param index The index to be checked.
     * @return true if the index is valid, false otherwise.
     */
    private boolean indexValid(int index)
    {
        // The return value.
        // Set according to whether the index is valid or not.
        boolean valid;
        
        if(index < 0) {
            System.out.println("Index cannot be negative: " + index);
            valid = false;
        }
        else if(index >= tracks.size()) {
            System.out.println("Index is too large: " + index);
            valid = false;
        }
        else {
            valid = true;
        }
        return valid;
    }
    
    private void readLibrary(String folderName)
    {
        ArrayList<Track> tempTracks = reader.readTracks(folderName, ".mp3");

        // Put all thetracks into the organizer.
        for(Track track : tempTracks) {
            addTrack(track);
        }
    }
}
