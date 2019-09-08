package com.kk.design.adapter;

public class Mp4Player implements AdvanceMediaPlayer {
    @Override
    public void playVlc(String fileName) {
        //什么都不做
    }

    @Override
    public void playMp4(String fileName) {
        System.out.println("Playing mp4 file: " + fileName);
    }
}
