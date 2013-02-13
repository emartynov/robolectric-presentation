package com.ebuddy.events;

public class DownloadedData
{
    private final String mimeType;
    private final byte[] data;

    public DownloadedData ( String mimeType, byte[] data )
    {
        this.mimeType = mimeType;
        this.data = data;
    }

    public String getMimeType ()
    {
        return mimeType;
    }

    public byte[] getData ()
    {
        return data;
    }
}
