package com.kk.design.adapter;

public class AudioPlayer implements MediaPlayer {
    private MediaAdapter mediaAdapter;
    @Override
    public void play(String audioType, String fileName) {
        if (audioType.equalsIgnoreCase("mp3")) {
            System.out.println("Playing mp3 file: " + fileName);
        } else if (audioType.equalsIgnoreCase("vlc")
                        || audioType.equalsIgnoreCase("mp4")){
            mediaAdapter = new MediaAdapter(audioType);
            mediaAdapter.play(audioType,fileName);
        } else {
            //没有适配这种类型的
            System.out.println("Sorry! Don't support " + audioType + " file!");
        }
    }
}
