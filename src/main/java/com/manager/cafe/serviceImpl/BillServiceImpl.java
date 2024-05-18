package com.manager.cafe.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.manager.cafe.JWT.jwt.JwtFilter;
import com.manager.cafe.POJO.Bill;
import com.manager.cafe.constents.CafeConstants;
import com.manager.cafe.dao.BillDao;
import com.manager.cafe.service.BillService;
import com.manager.cafe.utils.CafeUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BillServiceImpl implements BillService{

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    BillDao billDao;

    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
       try {
            String fileName;
            log.info(requestMap+"");
            if(validateRequestMap(requestMap)){
                if(requestMap.containsKey("isGenerate")&& !(Boolean) requestMap.get("isGenerate")){
                    fileName=(String) requestMap.get("uuid");
                }else{
                    fileName=CafeUtils.getUUID();
                    requestMap.put("uuid", fileName);
                    insertBill(requestMap);
                }

                String data="Name: "+requestMap.get("name")+"\n"+"Contact Number:"+requestMap.get("contactNumber")+
                    "\n"+"Email: "+ requestMap.get("email")+"\n"+"Payment Method: "+requestMap.get("paymentMethod");
                Document document=new Document(); 
                PdfWriter.getInstance(document, new FileOutputStream(CafeConstants.STORE_LOCATION+"\\"+fileName+".pdf"));
                document.open();
                setRactangleInPdf(document);

                Paragraph chunk=new Paragraph("Cafe Manger System", getFont("Header"));
                chunk.setAlignment(Element.ALIGN_CENTER);
                document.add(chunk);

                Paragraph paragraph=new Paragraph(data+"\n \n", getFont("Data"));
                document.add(paragraph);

                PdfPTable table=new PdfPTable(5);
                table.setWidthPercentage(100);
                addTableHeader(table);

                JSONArray jsonArray=CafeUtils.getJsonArrayfromString((String)requestMap.get("productDetails"));
                log.info(jsonArray+"");
                for(int i=0; i<jsonArray.length();i++){
                    log.info("Helloj"+jsonArray.getJSONObject(i));
                    addRows(table, CafeUtils.getMapFormJson(jsonArray.getJSONObject(i).toString()));
                }
                document.add(table);

                Paragraph footer=new Paragraph("Total :"+requestMap.get("total")+"\n"
                +"Thank you for visiting. Please visit again!!",getFont("Data"));
                document.add(footer);

                document.close();
                return new ResponseEntity<>("{\"uuid\":\""+fileName+"\"}",HttpStatus.OK);

            }
            return CafeUtils.getResponseEntity("Required Data not found ", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();// TODO: handle exception
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    

    private void addRows(PdfPTable table, Map<String, Object> data) {
        table.addCell((String) data.get("name"));
        table.addCell((String) data.get("category"));
        table.addCell((String) data.get("quantity"));
        table.addCell(Double.toString((Double)data.get("price")));
        table.addCell(Double.toString((Double)data.get("total")));
    }



    private void addTableHeader(PdfPTable table) {
        Stream.of("Name","Category","Quantity","Price","Sub Total")
            .forEach(columTitle->{
                PdfPCell header=new PdfPCell();
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setBorderWidth(2);
                header.setBorderColor(BaseColor.BLACK);
                header.setPhrase(new Phrase(columTitle));
                header.setBackgroundColor(BaseColor.YELLOW);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setVerticalAlignment(Element.ALIGN_CENTER);
                table.addCell(header);
            });
    }

    private Font getFont(String type) {
        switch (type) {
            case "Header":
                Font heaFont=FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE,18,BaseColor.BLACK);
                heaFont.setStyle(Font.BOLD);
                return heaFont;
                //break;
            case "Data":
                Font heaFontDT=FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK);
                heaFontDT.setStyle(Font.BOLD);
                return heaFontDT;
            default:
                return new Font();
        }
    }

    private void setRactangleInPdf(Document document) throws DocumentException{
       Rectangle rect=new Rectangle(577,825,18,15);
       rect.enableBorderSide(1);
       rect.enableBorderSide(2);
       rect.enableBorderSide(4);
       rect.enableBorderSide(8);
       rect.setBorderColor(BaseColor.BLACK);
       rect.setBorderWidth(1);
       document.add(rect);
    }

    private void insertBill(Map<String, Object> requestMap) {
        try {
           Bill bill=new Bill();
           bill.setUuid((String) requestMap.get("uuid"));
           bill.setName((String)requestMap.get("name"));
           bill.setEmail((String)requestMap.get("email"));
           bill.setContactNumber((String)requestMap.get("contactNumber"));
           bill.setPaymentMethod((String)requestMap.get("paymentMethod"));
           bill.setTotal(Integer.parseInt((String) requestMap.get("total")));
           bill.setProductDetail((String) requestMap.get("productDetails"));
           bill.setCreateBy(jwtFilter.getCurrentUser());
           billDao.save(bill);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateRequestMap(Map<String, Object> requestMap) {
        return requestMap.containsKey("name")&&
        requestMap.containsKey("contactNumber")&&
        requestMap.containsKey("email")&&
        requestMap.containsKey("paymentMethod")&&
        requestMap.containsKey("productDetails")&&
        requestMap.containsKey("total");
    }



    @Override
    public ResponseEntity<List<Bill>> getBills() {
       List<Bill> list=new ArrayList<>();
       if(jwtFilter.isAdmin()){
            list=billDao.getAllBills();
       }else{
            list=billDao.getBillByUserName(jwtFilter.getCurrentUser());
       }
       return new ResponseEntity<>(list,HttpStatus.OK);
    }



    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
        try {
            byte[] byteAr=new byte[0];
            if(!requestMap.containsKey("uuid") && validateRequestMap(requestMap))
                return new ResponseEntity<>(byteAr,HttpStatus.BAD_REQUEST);
            String filePath=CafeConstants.STORE_LOCATION+"\\"+(String) requestMap.get("uuid")+".pdf";
            if(CafeUtils.isFileExist(filePath)){
                byteAr=getByteArray(filePath);
                return new ResponseEntity<>(byteAr,HttpStatus.OK);
            }else{
                requestMap.put("isGenerate",false);
                generateReport(requestMap);
                byteAr=getByteArray(filePath);
                return new ResponseEntity<>(byteAr,HttpStatus.OK);
            }
            //return billService.getPdf(requestMap);
        } catch (Exception e) {
            e.printStackTrace();// TODO: handle exception
        }
        return null;
    }



    private byte[] getByteArray(String filePath) throws Exception{
       File initialFile =new File(filePath);
        InputStream tangetStream=new FileInputStream(initialFile);
       byte[] byteArr= IOUtils.toByteArray(tangetStream);
       tangetStream.close();
       return byteArr;
    }
    
}
