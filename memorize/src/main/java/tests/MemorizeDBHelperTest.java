import com.example.memorize.memorize.MemorizeDBOpenHelper;

import junit.framework.TestCase;

import java.util.Random;


/**
 * Created by ramp on 8/25/14.
 */
public class MemorizeDBHelperTest extends TestCase {
    public void testIntToByteAndBack() {
        final int count = 100;
        int[] in = new int[count];
        Random r = new Random();
        for (int i=0;i<count;++i) {
            in[i] = r.nextInt();
        }
        byte[] out = new byte[in.length * 4];
        MemorizeDBOpenHelper.convertIntArrayToByteArray(in, out);
        int[] int_out = new int[count];
        MemorizeDBOpenHelper.convertByteArrayToIntArray(out, int_out);

        for (int i=0;i<count;++i) {
            assertEquals(String.format("%d != %d at %d", in[i], int_out[i], i), in[i], int_out[i]);
        }
    }

    public void testConversion() {
        int in[] = new int[] {0x01010101,0xaabbccdd,0x02020202};
        byte out[] = new byte[12];
        int out_out[] = new int[3];

        MemorizeDBOpenHelper.convertIntArrayToByteArray(in, out);
        MemorizeDBOpenHelper.convertByteArrayToIntArray(out, out_out);

        for (int i=0;i<3;++i) {
            assertEquals(String.format("%d != %d at %d", in[i], out_out[i], i), in[i], out_out[i]);
        }
    }

    public void testArgs() {

    }
}
