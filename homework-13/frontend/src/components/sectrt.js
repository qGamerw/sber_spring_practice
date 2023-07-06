import logo from '../audio/gatchi.mp3';
import logo1 from '../audio/img.png';
import React from 'react';


function ImageComponent() {
    return (
        <div>
            <img src={logo1} alt="описание_картинки" />
        </div>
    );
}


function AudioPlayer() {
    return (
        <div>
            <ImageComponent/>
            <audio src={logo} controls />
        </div>
    );
}

export default AudioPlayer;