package com.test.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.util.IoUtils;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import com.alibaba.excel.write.style.row.SimpleRowHeightStyleStrategy;
import com.test.dto.DemoExcelInput;
import com.test.util.ExcelReadImageUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

/**
 * @author: laizc
 * @date: created in 2024/5/19
 * @desc:
 **/
@Service
@Slf4j
public class ExcelServiceImpl implements ExcelService{


    @Override
    public List<DemoExcelInput> easyImport(MultipartFile multipartFile) {
        InputStream inputStream = null;
        List<DemoExcelInput> demoExcelInputs = null;
        try {
            inputStream = multipartFile.getInputStream();
            demoExcelInputs = EasyExcelFactory.read(multipartFile.getInputStream())
                .head(DemoExcelInput.class).sheet().doReadSync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ExcelReadImageUtil.readImage(inputStream,demoExcelInputs);
        log.info(demoExcelInputs.toString());
        return demoExcelInputs;
    }

    @Override
    public void easyDownload(HttpServletResponse response) throws IOException {
        List<DemoExcelInput> demoExcelInputs = new ArrayList<>();
        DemoExcelInput demoExcelInput = new DemoExcelInput();
        demoExcelInput.setName("aa");
        String url = "https://p26-passport.byteacctimg.com/img/user-avatar/82b069ce17bb5b0eccb7ee67d3f6f3bc~180x180.awebp";
        demoExcelInput.setImageStr(url);
        demoExcelInput.setImageUrl(new URL(url));
        demoExcelInputs.add(demoExcelInput);

        InputStream inputStream = new URL(url).openStream();
        demoExcelInput.setInputStream(inputStream);
        byte[] bytes = IoUtils.toByteArray(new URL(url).openStream());
        demoExcelInput.setBytes(bytes);

        // 使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName= "导出excel模板";
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString()).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition","attachment;filename*=utf-8''"+encodedFileName+".xlsx");
        EasyExcel.write(response.getOutputStream(),DemoExcelInput.class).sheet("模板").doWrite(demoExcelInputs);

    }

    @Override
    public void userDefinedExport(HttpServletResponse response) throws IOException, InvalidFormatException {
        List<Map<String, Object>> sheetDatas = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        List<DemoExcelInput> demoExcelInputList = new ArrayList<>();
        DemoExcelInput input = new DemoExcelInput();
        input.setName("aa");
        input.setImageStr("图片");
        demoExcelInputList.add(input);
        DemoExcelInput input2 = new DemoExcelInput();
        input2.setName("bb");
        input2.setImageStr("图片2");
        demoExcelInputList.add(input2);
        map.put("list", demoExcelInputList);
        map.put("fullName","腾讯企业");
        sheetDatas.add(map);
        File file = ResourceUtils.getFile("classpath:excel/statement.xlsx");

        InputStream in = new BufferedInputStream(new FileInputStream(file));
        XLSTransformer transformer = new XLSTransformer();
        Workbook workbook = transformer.transformXLS(in, map);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        // 获取内存缓冲中的数据
        byte[] content = out.toByteArray();
        // 将字节数组转化为输入流
        InputStream inputStream = new ByteArrayInputStream(content);
        String newFile = "test.xlsx";
        // 文件中文名添加"iso-8859-1"防止乱码
        response.setHeader("Content-Disposition", "attachment; filename=" + new String((newFile).getBytes("UTF-8"), "iso-8859-1"));
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        ServletOutputStream outputStream = response.getOutputStream();
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        BufferedOutputStream bos = new BufferedOutputStream(outputStream);
        byte[] buff = new byte[8192];
        int bytesRead;
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }
        bis.close();
        bos.close();
        outputStream.flush();
        outputStream.close();
    }

    @Override
    public void dynamicExport(HttpServletResponse response) throws IOException {
        List<LinkedHashMap<String,Object>> mapList = new ArrayList<>();
        LinkedHashMap<String,Object> map1 = new LinkedHashMap<>();
        map1.put("姓名","张三");
        map1.put("年龄",18);
        map1.put("性别","男");
        map1.put("学历","小学");
        LinkedHashMap<String,Object> map2 = new LinkedHashMap<>();
        map2.put("姓名","赵四");
        map2.put("年龄",21);
        map2.put("性别","男");
        mapList.add(map1);
        mapList.add(map2);
        LinkedHashMap<String, Object> first = mapList.stream()
                .max(Comparator.comparingInt(Map::size))
                .orElse(null);
        List<List<String>> head = first.keySet().stream()
                .map(Collections::singletonList)
                .collect(Collectors.toList());
        List<String> keys = new ArrayList<>(first.keySet());
        List<List<Object>> data = mapList.stream()
                .map(m -> {
                    List<Object> row = new ArrayList<>();
                    for (String key : keys) {
                        row.add(m.get(key) == null ? "" : m.get(key));
                    }
                    return row;
                }).collect(Collectors.toList());
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        EasyExcel.write(response.getOutputStream())
                // 这里放入动态头
                .head(head)
                .registerWriteHandler(new SimpleRowHeightStyleStrategy((short)80, (short)20))
                .registerWriteHandler(new SimpleColumnWidthStyleStrategy(20))
                .sheet("动态表单")
                // 相同表头不合并
                .automaticMergeHead(false)
                .doWrite(data);
    }


    @Override
    public void dynamicExportSecond(HttpServletResponse response) throws IOException {
        Questions q1 = new Questions(1,"问题1",true);
        Questions q2 = new Questions(2,"问题2",false);
        Questions q3 = new Questions(3,"问题3",true);
        List<Questions> questions = Arrays.asList(q1, q2,q3);

        Map<Integer,Questions> questionsMap = questions.stream().collect(toMap(Questions::getId, Function.identity()));
        Pair<List<List<String>>,List<String>> headResult = buildHead(questions);
        List<List<String>> data = new ArrayList<>();
        //Answer a1 = new Answer(1,"答案1","原因1");
        Answer a2 = new Answer(1,2,"答案2",null);
        Answer a3 = new Answer(1,3,"答案3","原因3");
        Answer a4 = new Answer(2,1,"答案1，回答2",null);
        Answer a5 = new Answer(2,3,"答案3，回答2","原因3，回答2");
        List<Answer> answers = Arrays.asList(a2,a3,a4,a5);
        Map<Integer,Map<Integer,Answer>> answerUserIdMap = answers.stream().collect(Collectors.groupingBy(Answer::getAnswerUserId
                ,Collectors.toMap(Answer::getQuestionId,Function.identity(),(e1,e2)->e1,LinkedHashMap::new)));
        AnswerUser u1 = new AnswerUser(1,"张三","12345678901");
        AnswerUser u2 = new AnswerUser(2,"李四","12345678902");
        List<AnswerUser> answerUsers = Arrays.asList(u1,u2);
        List<String> questionsIdList = headResult.getRight();
        answerUsers.stream().forEach(answerUser -> {
            List<String> row = new ArrayList<>();
            row.add(answerUser.getName());
            row.add(answerUser.getPhone());
            Integer answerUserId = answerUser.getId();
            Map<Integer, Answer> questionIdAnswerMap = answerUserIdMap.get(answerUserId);
            if (questionIdAnswerMap != null) {
                for (int i = 0; i < questionsIdList.size(); i++) {
                    String questionId = questionsIdList.get(i);
                    boolean reason = false;
                    if (questionId.endsWith("_reason")) {
                        reason = true;
                        questionId = questionId.substring(0, questionId.length() - 7);
                    }
                    Integer questionIdInt = Integer.valueOf(questionId);

                    Answer answer = questionIdAnswerMap.get(questionIdInt);
                    if (answer != null) {
                        if (!reason) {
                            row.add(answer.getAnswer() == null ? "" : answer.getAnswer());
                        } else {
                            row.add(answer.getReason() == null ? "" : answer.getReason());
                        }
                    } else {
                        row.add("");
                    }
                }
            }
            data.add(row);
        });
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        EasyExcel.write(response.getOutputStream())
                // 这里放入动态头
                .head(headResult.getLeft())
                .registerWriteHandler(new SimpleRowHeightStyleStrategy((short)80, (short)20))
                .registerWriteHandler(new SimpleColumnWidthStyleStrategy(20))
                .sheet("动态表单")
                // 相同表头不合并
                .automaticMergeHead(false)
                .doWrite(data);














    }

    private Pair<List<List<String>>,List<String>> buildHead(List<Questions> questions) {
        List<List<String>> head = new ArrayList<>();
        head.add(Collections.singletonList("姓名"));
        head.add(Collections.singletonList("手机号"));
        List<String> questionIdList = new ArrayList<>();
        questions.stream().forEach(question -> {
            String text = question.getText();
            head.add(Collections.singletonList(text));
            questionIdList.add(String.valueOf(question.getId()));
            Boolean needReason = question.getNeedReason() == null ? false : question.getNeedReason();
            if (needReason) {
                head.add(Collections.singletonList(text + "（原因）"));
                questionIdList.add(question.getId() + "_reason");
            }
        });
        return Pair.of(head,questionIdList);
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Questions{

        private Integer id;

        /**
         * 问题文本
         */
        private String text;

        /**
         * 是否需要原因
         */
        private Boolean needReason;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Answer {

        /**
         * 回答人id
         */
        private Integer answerUserId;

        /**
         * 问题id
         */
        private Integer questionId;

        /**
         * 回答文本
         */
        private String answer;

        /**
         * 回答原因
         */
        private String reason;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class AnswerUser{

        private Integer id;

        private String name;

        private String phone;
    }


}
