import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class JSONFormater {
    public byte[] format(byte[] jsonStr){
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(jsonStr);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            char ch;
            char last =' ';
            int read;
            int space=0;
            while((read = in.read()) > 0){
                ch = (char)read;
                switch (ch){
                    case '{': {
                        if(last=='{' || last==',') //如果前面是右括号 只需一次换行 如果前面是逗号，逗号处理已经换行，所以只需输出并换行
                            space = outputAndRightMove(space, ch, out);
                        else //换行再输出
                            space = outputNextLineAndRightMove(space, ch, out);
                        break;
                    }
                    case '}': {
                        if(last==',') //退格两个后输出 并换行
                            space = outputBackAndLeftMove(space, ch, out);
                        else
                            space = outputAndLeftMove(space, ch, out);
                        //last = true;
                        break;
                    }
                    case ',': {
                        out.write(ch);
                        outputNewline(out);
                        out.write(getBlankingStringBytes(space));
                        break;
                    }
                    case ' ':{
                        if(last==',')
                            break;
                        else
                        {
                            out.write(ch);
                            break;
                        }
                    }
                    default: {
                        out.write(ch);
                        break;
                    }
                }
                last = ch;
            }
            return out.toString().getBytes();
        } catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }
    private  int outputAndRightMove(int space, char ch, ByteArrayOutputStream out) throws IOException {
        out.write(ch);
        outputNewline(out);
        space += 2;
        //再向右缩进多两个字符
        out.write(getBlankingStringBytes(space));
        return space;
    }
    private  int outputNextLineAndRightMove(int space, char ch, ByteArrayOutputStream out) throws IOException {
        //换行
        outputNewline(out);
        //向右缩进
        out.write(getBlankingStringBytes(space));
        out.write(ch);
        outputNewline(out);
        space += 2;
        //再向右缩进多两个字符
        out.write(getBlankingStringBytes(space));
        return space;
    }

    private  int outputAndLeftMove(int space, char ch, ByteArrayOutputStream out) throws IOException{
        outputNewline(out);
        space -= 2;
        out.write(getBlankingStringBytes(space));
        out.write(ch);
        return space;
    }
    private  int outputBackAndLeftMove(int space, char ch, ByteArrayOutputStream out) throws IOException {
        space -= 2;
        out.write(getBlankingStringBytes(space));
        out.write(ch);
        return space;
    }
    private  byte[] getBlankingStringBytes(int space){
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < space; i++) {
            sb.append(" ");
        }
        return sb.toString().getBytes();
    }

    private  void outputNewline(ByteArrayOutputStream out){
        out.write('\n');
    }
}
