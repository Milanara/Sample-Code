/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package srcFiles;

/**
 *
 * @author Owner
 */
public class Packet {
    byte[] header; // 6 bytes
        //bytes 1 and 2- sequence num
        //bytes 3 and 4 - ack num
        //byte 5 - Rec Window
        // byte 6 - flags (bit 1 - Ack contained, bit 2 - connection request
            //bit 3 - close request
    byte[] data;
    public byte[] getData(){
        return data;
    }
    public byte[] getWholePacket(){
        byte[] whole = new byte[6+data.length];
        int i;
        for (i = 0; i< 5; i++){
            whole[i] = header[i];
        }
        for(int j = 0; j<data.length; j++){
            whole[i] = data[j];
            i++;
        }
        return whole;
    }
    public void packetDebug(){
        System.out.println("PacketNum: " + getPacketNum() + " Ack Num: "+ getAck());
        int rec = (int) getWindow();
        System.out.println("Rec Window: " + rec + " Ack Flag: " + getIfAck()
                + " ConnecFlag: " + getIfConnect() + " Close Flag: " + getIfClose());
        String debug = new String(data);
        System.out.println(debug);
    }
    public byte[] getHeader(){
        return header;
    }
    public int getPacketNum(){
        int tmp = (int)(header[0] & 0xFF);
        tmp = tmp << 8;
        tmp = tmp + (int)(header[1]&0xFF);
        return tmp;
    }
    public int getAck(){
        int tmp = (int)(header[2]&0xFF);
        tmp = tmp << 8;
        tmp = tmp + (int) (header[3]&0xFF);
        return tmp;
    }
    public char getWindow(){
        return (char) (header[4]&0xFF);
    }
    public boolean getIfAck(){
        //returns whether or there is a valid ack
        int tmp, a;
        a = 0x00000080;
        tmp = header[5];
        tmp = a & tmp;
        if(tmp == 0) return false;
        return true;
    }
    public boolean getIfConnect(){
        //returns whether or there is a valid ack
        int tmp, a;
        a = 0x00000040;
        tmp = header[5];
        tmp = a & tmp;
        if(tmp == 0) return false;
        return true;
    }
    public boolean getIfClose(){
        //returns whether or there is a valid ack
        int tmp, a;
        a = 0x00000020;
        tmp = header[5];
        tmp = a & tmp;
        if(tmp == 0) return false;
        return true;
    }
    public Packet(int seqNum, int ackNum, byte window, String flags, byte[] info){
        header = new byte[6];
        data = info;
        //pack header
        //sequence num
        header[1] = (byte)seqNum;
        header[0] = (byte)(seqNum >> 8);
        //ack num
        header[3] = (byte)ackNum;
        header[2] = (byte)(ackNum >> 8);
        //window
        header[4] = window;
        //convert binary flag string to flag byte
        header[5] = new java.math.BigInteger(flags,2).byteValue();
    }
    
    public Packet(byte[] packet){
        header = new byte[6];
        data = new byte[1018];
        int i;
        for (i = 0; i< 5; i++){
            header[i] = packet[i];
        }
        for(int j = 0; i<1024; i++){
            data[j] = packet[i];
            j++;
        }
    }

}
