//package cash.tag;
//
//import javax.servlet.jsp.JspWriter;
//import javax.servlet.jsp.tagext.TagSupport;
//import java.util.Calendar;
//
//public class TagCurrentDateTimeHandler extends TagSupport{
//    public int doStartTag()  {
//        System.out.println(" doStartTag()");
//        JspWriter out=pageContext.getOut();//returns the instance of JspWriter
//        try{
//            out.print(Calendar.getInstance().getTime());//printing date and time using JspWriter
//        }catch(Exception e){System.out.println(e);}
//        return SKIP_BODY;//will not evaluate the body content of the tag
//    }
//}
