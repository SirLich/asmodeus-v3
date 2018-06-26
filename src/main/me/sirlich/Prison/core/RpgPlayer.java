package main.me.sirlich.Prison.core;

public class RpgPlayer
{
    public PlayerState getPlayerState()
    {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState)
    {
        this.playerState = playerState;
    }

    private PlayerState playerState;
}
