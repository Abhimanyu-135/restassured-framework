package utilities;

import reporting.TestListener;

public class EmailBodyBuilder {

    public static String buildBody() {

        StringBuilder failedList = new StringBuilder();
        String build="build no#"+System.currentTimeMillis();

        for(String test : TestListener.failedTests) {
            failedList.append("<li>").append(test).append("</li>");
        }

        String body =
        "<html><body>" +
        "<br>Hello Everyone<br>"+

        "<h2>API Automation Execution Report</h2>" +

        "<table border='1' cellpadding='8' cellspacing='0'>" +
        "<tr style='background-color:#f2f2f2'>" +
        "<th>Total</th><th>Passed</th><th>Failed</th><th>Skipped</th>" +
        "</tr>" +

        "<tr>" +
        "<td>"+TestListener.total+"</td>" +
        "<td style='color:green'>"+TestListener.passed+"</td>" +
        "<td style='color:red'>"+TestListener.failed+"</td>" +
        "<td style='color:orange'>"+TestListener.skipped+"</td>" +
        "</tr>" +
        "</table>" +

        "<br>" +

        "<b>Execution Environment:</b> QA<br>" +
        "<b>Build:</b>"+build+"<br>" +
        "<b>Report:</b> Extent Report Attached<br><br>";

        if(TestListener.failed > 0) {

            body += "<h3>Failed Test Cases</h3>";
            body += "<ul>" + failedList + "</ul>";
        }

        body +=
                "<br><br><b>Thanks and Regards</b><br>" +
                "Abhimanyu Tiwary<br>" +
                "</body></html>";

        return body;
    }
}