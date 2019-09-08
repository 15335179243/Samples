package com.kk.design.adapter;

public class Demo {

    public static void main(String[] args) {
        AudioPlayer player = new AudioPlayer();
        player.play("mp3","Dangerous.mp3");
        player.play("mp4","Dangerous.mp4");
        player.play("vlc","Dangerous.vlc");
        player.play("avi","Dangerous.avi");
    }
}
