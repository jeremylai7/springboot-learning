<html>
<head>
    <meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>购销合同</title>
    <style>
        @page {
            size: a4;
            margin-left: 0px;
            margin-right: 0px;
        }

        .clearfix::after {
            content: " ";
            display: block;
            clear: both;
            height: 0;
            visibility: hidden;
        }

        body, p {
            margin: 0;
            padding: 0;
            font-size: 14.5px;
            line-height: 2.0;
        }

        body {
            font-weight: 700;
            font-family: SimSun;
        }

        h1, h2, h3 {
            text-align: center;
        }

        .fl-left {
            float: left;
        }

        .fl-right {
            margin-right: 55px;
            float: right;
        }

        .al-center {
            text-align: center;
        }

        span {
            display: inline-block;
        }

        .title {
            font-size: 22px;
            text-align: center;
            margin-bottom: 10px;
        }

        .contract {
            width: 730px;
            height: 978px;
            margin: 0 auto;
        }

        .money {
            width: 99.8%;
            border: 1px solid #000;
            margin-top: -1px;
            text-align: center;
        }

        .remark {
            width: 99.8%;
            border: 1px solid #000;
            margin-top: -1px;
            height: 25px;
            line-height: 25px;
        }

        .money span {
            border-left: 1px solid #000;
            display: inline-block;
            float: left;
            margin-left: -1px;
            padding: 5px 0;
        }

        .money span:first {
            margin-left: 0;
            border-left: none;
        }

        .wordBreak {
            width: 95%;
        }

        .underLine {
            position: relative;
            text-align: center;
            display: inline-block;
        }

        .underLine::after {
            width: 100%;
            height: 1px;
            background: #000;
            display: inline-block;
            content: '';
            position: absolute;
            bottom: 1px;
            left: 0;
        }

        .underLine_text {
            padding-bottom: 1px;
            border-bottom: 1px solid #000;
        }

        .address::after {
            width: 205px;
        }

        .otherRemark.underLine::after {
            width: 450px;
        }

        .signature p {
            line-height: 35px;
        }
    </style>
</head>
<body>
<!-- 买方自提 -->
<div class="contract">
    <div class="title">购&#160;&#160;销&#160;&#160;合&#160;&#160;同</div>

    <div class="clearfix">
        <div class="fl-left">
            <p style="margin-bottom: 20px;">卖方(甲方): {contractEnterprise}</p>
            <p>买方(乙方): {buyers}</p>
        </div>
        <div class="fl-right">
            <p>合同编码: {contractCode}</p>
            <p>签订地点: {signPlace}</p>
            <p>签订日期: {signDate}</p>
        </div>
    </div>
    <div class="al-center">甲乙双方经协商一致,根据《中华人民共和国民法典》以及其他法律法规,达成以下油品买卖协议:</div>
    <div>一、产品名称、型号、数量、金额:</div>
    <table border="1" style="width: 100%; border-collapse: collapse; text-align: center;margin-top: 5px;">
        <thead>
        <tr>
            <td>序号</td>
            <td>货品名称</td>
            <td>包装方式</td>
            <td>单位</td>
            <td>数量</td>
            <td>含税单价(元)</td>
            <td>含税金额(元)</td>
            <#--<#if deliveryMode = 2>
                <td>提货地点</td>
            </#if>-->
            <td>备注</td>
        </tr>
        </thead>
        <tbody>
                <#--<#list orderList as order>-->
                <tr>
                    <td>{order}</td>
                    <td style="width: 150px;line-height: 1;">{order.goodsName}</td>
                    <td>{order.packingName}</td>
                    <td>{order.unitName}</td>
                    <td>{order.num}</td>
                    <td>{order.price?string('0.00')}</td>
                    <td>{order.amount?string('0.00')}</td>
                    <#--<#if deliveryMode = 2>
                        <td style="max-width: 100px">${order.oilDepotName}</td>
                    </#if>-->
                    <td style="width: 100px;line-height: 1;">{order.goodsSimpName!}</td>
                </tr>
                <#--</#list>-->

        </tbody>
    </table>
    <!-- 金额 -->
    <table border="1" style="width: 100%; border-collapse: collapse;margin-bottom: 5px">
        <tbody>
        <tr style="line-height: 30px;text-align: center">
            <td style="">合计人民币大写：</td>
            <td class="capital" style="">
            {cnAmount}
            </td>
            <td style="">小写：</td>
            <td style="">{amount?string('0.00')}</td>
        </tr>

        <tr>
            <td colspan='4' class="remark">{orderRemark}</td>
        </tr>
        </tbody>
    </table>
    <div class="sub_title clearfix">
        <div class="fl-left">二、</div>
        <div class="fl-left wordBreak">

        </div>
    </div>
    <div class="sub_title clearfix">
        <div class="fl-left">三、</div>
        <div class="fl-left wordBreak">
            交货期限:
            <span class="underLine" style="width: 100px;">{deliveryLimit}</span>前（具体交付时间以甲方通知为准）。乙方应在甲方通知提货的3天内完成提货，否则因此造成的损失由乙方承担。如乙方迟迟不收货，我司可以代为保管，但其产生超期等相关系列费用（包括但不限于仓储费用、物流费用、处置费用等）需乙方承担支付，具体以双方实际超期数量再另行核算。
            如因甲方的供应商出现供货异常，甲方应提前3个工作日告知乙方。若在甲方通知供应商出现供货异常后的10日内，甲方供应商仍无法供货，甲方有权解除合同，并退回乙方已支付的款项，双方不承担任何违约责任。
        </div>
    </div>



</div>
</body>
</html>