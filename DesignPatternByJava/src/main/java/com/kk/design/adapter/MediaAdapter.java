package com.kk.design.adapter;

/**
 * 多媒体适配器
 */
public class MediaAdapter implements MediaPlayer{
    private AdvanceMediaPlayer advanceMediaPlayer;
    @Override
    public void play(String audioType, String fileName) {
        if (audioType.equalsIgnoreCase("vlc")) {
            advanceMediaPlayer.playVlc(fileName);
        } else if (audioType.equalsIgnoreCase("mp4")){
            advanceMediaPlayer.playMp4(fileName);
        }
    }

    public MediaAdapter (String mediaType){
        if (mediaType.equalsIgnoreCase("vlc")) {
            advanceMediaPlayer = new VlcPlayer();
        } else if (mediaType.equalsIgnoreCase("mp4")){
            advanceMediaPlayer = new Mp4Player();
        }
    }
}
