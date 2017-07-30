import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class MD4 {
    private static long[]   X = new long[16];
    private static long[]   M;
    private static long A = 0x67452301;
    private static long B = 0xefcdab89;
    private static long C = 0x98badcfe;
    private static long D = 0x10325476;

    //rot constants
    private static final int R1_1 = 3;
    private static final int R1_2 = 7;
    private static final int R1_3 = 11;
    private static final int R1_4 = 19;
    private static final int R2_1 = 3;
    private static final int R2_2 = 5;
    private static final int R2_3 = 9;
    private static final int R2_4 = 13;
    private static final int R3_1 = 3;
    private static final int R3_2 = 9;
    private static final int R3_3 = 11;
    private static final int R3_4 = 15;

    public static void main(String[] args) throws UnsupportedEncodingException {
       //get input message from StdInput
        List<Character> message = new ArrayList<Character>();
//        int i;
//        try {
//            InputStreamReader is = new InputStreamReader(System.in);
//
//            while ((i = is.read()) != -1) {
//                message.add((char) i);
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }

        //convert char array to byte arraylist
        //String str = message.stream().map(e->e.toString()).collect(Collectors.joining());
        String str = "Erat autem homo ex pharisaesis, Nicodemus nominae, princ";
        byte[] bytes = str.getBytes("UTF-8");
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        System.out.println(sb.toString());
        ArrayList<Long> Mlist = new ArrayList<Long>();
        for(int j = 0; j < bytes.length; j++){
            Mlist.add((long)bytes[j]);
        }
        System.out.println(Mlist);
        long prePaddingLength = Mlist.size();

        //pad array
        Mlist.add((long)0x80);
        while(Mlist.size() % 64 != 56){
            Mlist.add((long)0x0);
        }
        //add length
        Mlist.add(prePaddingLength);

        System.out.println(Mlist);
        //convert to basic array
        M = Mlist.stream().mapToLong(Long::valueOf).toArray();

        //do rounds
        for(int k = 0; k < M.length/16-1; k++){
            for(int m = 0; m < 15; m++){
                X[m] = M[k*16+m];
            }
            long AA = A;
            long BB = B;
            long CC = C;
            long DD = D;

            //R1
            A = R1(A,B,C,D, 0, R1_1); //1
            D = R1(D,A,B,C, 1, R1_2); //2
            C = R1(C,D,A,B, 2, R1_3); //3
            B = R1(B,C,D,A, 3, R1_4); //4

            A = R1(A,B,C,D, 4, R1_1); //5
            D = R1(D,A,B,C, 5, R1_2); //6
            C = R1(C,D,A,B, 6, R1_3); //7
            B = R1(B,C,D,A, 7, R1_4); //8

            A = R1(A,B,C,D, 8, R1_1); //9
            D = R1(D,A,B,C, 9, R1_2); //10
            C = R1(C,D,A,B, 10, R1_3); //11
            B = R1(B,C,D,A, 11, R1_4); //12

            A = R1(A,B,C,D, 12, R1_1); //13
            D = R1(D,A,B,C, 13, R1_2); //14
            C = R1(C,D,A,B, 14, R1_3); //15
            B = R1(B,C,D,A, 15, R1_4); //16


            //R2

            A = R2(A,B,C,D, 0, R2_1); //1
            D = R2(D,A,B,C, 4, R2_2); //2
            C = R2(C,D,A,B, 8, R2_3); //3
            B = R2(B,C,D,A, 12, R2_4); //4

            A = R2(A,B,C,D, 1, R2_1); //5
            D = R2(D,A,B,C, 5, R2_2); //6
            C = R2(C,D,A,B, 9, R2_3); //7
            B = R2(B,C,D,A, 13, R2_4); //8

            A = R2(A,B,C,D, 2, R2_1); //9
            D = R2(D,A,B,C, 6, R2_2); //10
            C = R2(C,D,A,B, 10, R2_3); //11
            B = R2(B,C,D,A, 14, R2_4); //12

            A = R2(A,B,C,D, 3, R2_1); //13
            D = R2(D,A,B,C, 7, R2_2); //14
            C = R2(C,D,A,B, 11, R2_3); //15
            B = R2(B,C,D,A, 15, R2_4); //16


            //R3

            A = R3(A,B,C,D, 0, R3_1); // 1
            D = R3(D,A,B,C, 8, R3_2); // 2
            C = R3(C,D,A,B, 4, R3_3); // 3
            B = R3(B,C,D,A, 12, R3_4); // 4

            A = R3(A,B,C,D, 2, R3_1); // 5
            D = R3(D,A,B,C, 10, R3_2); // 6
            C = R3(C,D,A,B, 6, R3_3); // 7
            B = R3(B,C,D,A, 14, R3_4); // 8

            A = R3(A,B,C,D, 1, R3_1); // 9
            D = R3(D,A,B,C, 9, R3_2); // 10
            C = R3(C,D,A,B, 5, R3_3); // 11
            B = R3(B,C,D,A, 13, R3_4); // 12

            A = R3(A,B,C,D, 3, R3_1); // 13
            D = R3(D,A,B,C, 11, R3_2); // 14
            C = R3(C,D,A,B, 7, R3_3); // 15
            B = R3(B,C,D,A, 15, R3_4); // 16

            //save state
            A+=AA;
            B+=BB;
            C+=CC;
            D+=DD;

            //print output
            System.out.println(A + " " + B + " " + C + " " + D);
            StringBuilder builder = new StringBuilder();

            builder.append(String.format("%02X %02X %02X %02X", A, B, C, D));
            System.out.println(builder.toString());

        }


    }

    //aux functions
    private static long F(long x, long y, long z)
    {
        return (x & y) | (~x & z);
    }

    private static long G( long x, long y, long z)
    {
        return (x & y) | (x & z) | (y & z);
    }

    private static long H(long x, long y, long z)
    {
        return x ^ y ^ z;
    }
    //round functions
    private static long R1(long a, long b, long c, long d, int k, int s){
        a = rotateLeft(a + F(b,c,d) + X[k], s);
        return a;
    }
    private static long R2(long a, long b, long c, long d, int k, int s){
        a = rotateLeft(a + G(b,c,d) + X[k] + 0x5A827999, s);
        return a;
    }
    private static long R3(long a, long b, long c, long d, int k, int s){
        a = rotateLeft(a + H(b,c,d) + X[k] + 0x6ED9EBA1, s);
        return a;
    }


    //shift x left by n bits
    private static long rotateLeft(long x, int n)
    {
        return (x << n) | (x >>> (32 - n));
    }

}
