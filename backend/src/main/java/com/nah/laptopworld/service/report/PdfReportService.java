package com.nah.laptopworld.service.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.nah.laptopworld.model.Order;
import com.nah.laptopworld.model.OrderDetail;
import com.nah.laptopworld.service.OrderService;
import com.nah.laptopworld.service.UserService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class PdfReportService {

    private final OrderService orderService;

    private final ServletContext servletContext;
    private final HttpServletRequest request;

    public PdfReportService(UserService userService,
                            OrderService orderService,
                            ServletContext servletContext,
                            HttpServletRequest request) {
        this.orderService = orderService;
        this.servletContext = servletContext;
        this.request = request;
    }


    public byte[] createPdfReportBytes(String reportName, long id) throws Exception {
        Document document = new Document(PageSize.A6, 20, 20, 20, 20);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, baos);

            String fontPath = this.servletContext.getRealPath("/resources/fonts/times.ttf");
            if (fontPath == null || !Files.exists(Paths.get(fontPath))) {
                 System.err.println("Không tìm thấy file font: /resources/fonts/times.ttf");
                 fontPath = null;
            }
            
            if (fontPath != null) {
                 FontFactory.register(fontPath, "Times New Roman");
            }
            
            Font font = FontFactory.getFont(fontPath != null ? "Times New Roman" : FontFactory.TIMES_ROMAN, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 4, Font.NORMAL, BaseColor.BLACK);
            Font fontRed = FontFactory.getFont(fontPath != null ? "Times New Roman" : FontFactory.TIMES_ROMAN, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 6, Font.BOLD, BaseColor.RED);
            Font fontBoldBig = FontFactory.getFont(fontPath != null ? "Times New Roman" : FontFactory.TIMES_ROMAN, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 6, Font.BOLD, BaseColor.BLACK);
            Font fontBold = FontFactory.getFont(fontPath != null ? "Times New Roman" : FontFactory.TIMES_ROMAN, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 4, Font.BOLD, BaseColor.BLACK);


            Optional<Order> orderOptional = this.orderService.fetchOrderById(id);
            if (orderOptional.isPresent()){
                document.open();
                Order order = orderOptional.get();
                List<OrderDetail> orderDetails = order.getOrderDetails();
                DecimalFormat df = new DecimalFormat("#");

                Paragraph storeName = new Paragraph("LaptopHaui", fontRed);
                document.add(storeName);

                Paragraph title = new Paragraph("Hoá đơn bán hàng", fontBoldBig);
                title.setAlignment(Element.ALIGN_RIGHT);
                document.add(title);

                Paragraph storeAddress = new Paragraph("Địa chỉ: Số 68, Cầu Diễn, Từ Liêm, Hà Nội", font );
                storeAddress.setAlignment(Element.ALIGN_LEFT);

                Paragraph storePhone = new Paragraph("Số điện thoại: 0987654321" , font);
                storePhone.setAlignment(Element.ALIGN_LEFT);

                String employyName = "N/A";
                HttpSession session = request.getSession(false);
                 if (session != null && session.getAttribute("fullName") != null) {
                     employyName = (String) session.getAttribute("fullName");
                 } else {
                     System.err.println("Không thể lấy tên nhân viên từ session.");
                 }
                Paragraph sellerName = new Paragraph("Nhân viên xuất hoá đơn: "+ employyName, font);

                document.add(storeAddress);
                document.add(storePhone);
                document.add(sellerName);

                document.add(new Paragraph(" "));

                Paragraph  customerName =  new Paragraph("Tên khách hàng: " + (order.getUser() != null ? order.getUser().getFullName() : "N/A"), font);
                customerName.setAlignment(Element.ALIGN_LEFT);
                document.add(customerName);

                Paragraph customerAddress =  new Paragraph("Địa chỉ: " + (order.getUser() != null ? order.getUser().getAddress() : "N/A"), font);
                customerAddress.setAlignment(Element.ALIGN_LEFT);
                document.add(customerAddress);

                document.add(new Paragraph(" "));


                PdfPTable table = new PdfPTable(5);
                float[] columnWidths = {1f, 3f, 1f, 2f, 2f};
                table.setWidths(columnWidths);
                table.setWidthPercentage(100);

                table.addCell(new Phrase("STT", font));
                table.addCell(new Phrase("Tên SP", font));
                table.addCell(new Phrase("SL", font));
                table.addCell(new Phrase("Đ.Giá", font));
                table.addCell(new Phrase("T.Tiền", font));

                int index = 1;
                double sum =0;
                for(OrderDetail orderDetail : orderDetails){
                    table.addCell(new Phrase(String.valueOf(index), font));
                    String productName = "N/A";
                    if (orderDetail.getLaptopVariant() != null && orderDetail.getLaptopVariant().getLaptopModel() != null) {
                        productName = orderDetail.getLaptopVariant().getLaptopModel().getName();
                    }
                    table.addCell(new Phrase(productName, font));
                    table.addCell(new Phrase(String.valueOf(orderDetail.getQuantity()), font));
                    String formattedPrice = df.format(orderDetail.getPrice());
                    table.addCell(new Phrase(formattedPrice, font));
                    String formattedTotal = df.format(orderDetail.getPrice() * orderDetail.getQuantity());
                    table.addCell(new Phrase(formattedTotal, font));
                    sum += orderDetail.getPrice() * orderDetail.getQuantity();
                    index++;
                }
                table.addCell("");
                table.addCell("");
                table.addCell("");
                table.addCell(new Phrase("Tổng cộng", fontBold));
                String formattedSum = df.format(sum);
                table.addCell(new Phrase(formattedSum, fontBold));
                document.add(table);

                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String formattedDate = currentDate.format(formatter);
                Paragraph time = new Paragraph("Hà Nội, ngày " + formattedDate, font);
                time.setAlignment(Element.ALIGN_RIGHT);
                time.setIndentationRight(20);
                document.add(new Paragraph(" "));
                document.add(time);
                
                document.close();
                writer.close();
                
                return baos.toByteArray();

            } else {
                System.err.println("Order with id " + id + " does not exist.");
                 return null;
            }
        } catch (DocumentException e) {
            System.err.println("Lỗi thao tác với tài liệu iText: " + e.getMessage());
            throw new RuntimeException("Lỗi tạo báo cáo PDF", e);
        } finally {
            if (document.isOpen()) {
                document.close();
            }
            baos.close();
        }
    }
}
