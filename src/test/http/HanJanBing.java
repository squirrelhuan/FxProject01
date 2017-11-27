package test.http;

import java.util.List;

/**
 * Created by huan on 2017/9/19.
 */
public class HanJanBing {


    /**
     * inf : {"adImage":[],"disease":[{"enTitle":"Abnormal cell proliferati","diseases":[{"name":"vhl综合征","id":"f4be9bee5c494735015c72ebbaf6036b"},{"name":"朗格汉斯组织细胞增多症","id":"f4be9bee5c494735015c72f2e09d036c"},{"name":"淋巴管平滑肌增生症","id":"f4be9bee5c494735015c730167f9036d"},{"name":"视网膜母细胞瘤","id":"f4be9bee5c494735015c7302faed036e"}],"id":"f4be9bee5af3e6ec015af42fb491000a","title":"不正常细胞增生（瘤）"},{"enTitle":"Congenital Metabolic Disorders","diseases":[{"name":"3-氢基-3-甲基戊二酸血症","id":"f4be9bee5ca44ca5015cab27aca00088"},{"name":"Beta硫解酶缺乏症","id":"f4be9bee5c48bdc3015c48e91a8a0000"},{"name":"Fabry氏症","id":"f4be9bee5ca44ca5015cab0ee6d4007d"},{"name":"GM1神经节苷脂储积症","id":"f4be9bee5ca44ca5015cab125dcb007f"},{"name":"GM2神经节苷脂储积症","id":"f4be9bee5ca44ca5015cab107bbd007e"}],"id":"f4be9bee5af3e6ec015af4324146000b","title":"先天性代谢异常"},{"enTitle":"Brain / Nervous System Diseases","diseases":[{"name":"Aicardi-Goutieres综合征","id":"f4be9bee5c7b44e1015c7e6312e6008f"},{"name":"Charcot Maire Tooth 氏症","id":"f4be9bee5c807455015c87c946e000ec"},{"name":"Joubert氏综合征","id":"f4be9bee5c807455015c87f6707600f3"},{"name":"下丘脑功能障碍综合征","id":"f4be9bee5c7b44e1015c7e645ac80090"},{"name":"亚历山大症","id":"f4be9bee5c807455015c87ff3eea00f6"}],"id":"f4be9bee5af3e6ec015af432b58b000c","title":"脑部或神经系统病变"},{"enTitle":"Respiratory / Circulatory System Diseases","diseases":[{"name":"Andersen氏综合征","id":"f4be9bee5c807455015c8822a3190109"},{"name":"Holt-Oram氏综合征","id":"f4be9bee5c807455015c8824c462010a"},{"name":"原发性肺血铁质沉积症","id":"f4be9bee5c807455015c8816d1d40104"},{"name":"囊性纤维化症","id":"f4be9bee5c807455015c8834072f010c"},{"name":"特发性婴儿动脉硬化症","id":"f4be9bee5c807455015c884b2287010d"}],"id":"f4be9bee5af3e6ec015af432e93e000d","title":"呼吸循环系统病变"},{"enTitle":"Digestive System Diseases","diseases":[{"name":"α1-抗胰蛋白酶缺乏症","id":"f4be9bee5c807455015c88a2bf0e0111"},{"name":"先天性Cajal氏间质细胞增生合并肠道神经元发育异常","id":"f4be9bee5c807455015c889f60910110"},{"name":"先天性胆酸合成障碍","id":"f4be9bee5c807455015c88a5aa7e0112"},{"name":"克罗恩病","id":"f4be9bee5c807455015c88967398010e"},{"name":"进行性家族性肝内胆汁滞留症","id":"f4be9bee5c807455015c88a846370113"}],"id":"f4be9bee5af3e6ec015af4332db9000e","title":"消化系统病变"},{"enTitle":"Renal / Urinary System Diseases","diseases":[{"name":"Alport综合征","id":"f4be9bee5c807455015c88ac82820116"},{"name":"Bartter氏综合征","id":"f4be9bee5c807455015c88afd9fa0118"},{"name":"Lowe 氏综合征","id":"f4be9bee5c807455015c88b208530119"},{"name":"家族性低血钾症","id":"f4be9bee5c807455015c88a95c7a0114"},{"name":"常染色体隐性多囊性肾脏疾病","id":"f4be9bee5c807455015c88ae03070117"}],"id":"f4be9bee5c494735015c5d51469300a1","title":"肾脏泌尿系统病变"},{"enTitle":"Skin Diseases","diseases":[{"name":"Meleda岛病","id":"f4be9bee5c494735015c717a69db030e"},{"name":"Netherton综合征","id":"f4be9bee5c494735015c7172df310309"},{"name":"先天性角化不全症","id":"f4be9bee5c494735015c71771e6a030c"},{"name":"外胚层增生不良症","id":"f4be9bee5c494735015c717b7b0d030f"},{"name":"婴儿型全身性玻璃样变性","id":"f4be9bee5c494735015c716eb19d0307"}],"id":"f4be9bee5c494735015c5d51de8e00a2","title":"皮肤病变"},{"enTitle":"Muscular Diseases","diseases":[{"name":"Nemaline线状肌肉病变","id":"f4be9bee5c7693c9015c781033a0009f"},{"name":"Schwartz Jampel 氏综合征","id":"f4be9bee5c7693c9015c780f2702009d"},{"name":"三好氏远端肌肉病变","id":"f4be9bee5c7693c9015c780581e5008c"},{"name":"弗里曼-谢尔登综合征","id":"f4be9bee5c7693c9015c7803af86008a"},{"name":"杜氏肌营养不良症","id":"f4be9bee5c7693c9015c7811651b00a2"}],"id":"f4be9bee5c494735015c5d52571900a3","title":"肌肉病变"},{"enTitle":"Bone / Cartilage Diseases","diseases":[{"name":"假性软骨发育不全","id":"f4be9bee5c7693c9015c781a758a00b2"},{"name":"先天性胫骨假关节","id":"f4be9bee5c7693c9015c7816286800a9"},{"name":"多发性骨骺发育不全症","id":"f4be9bee5c7693c9015c78193eef00b0"},{"name":"多发性骨髓瘤","id":"f4be9bee5c7693c9015c7817b2d000ac"},{"name":"成骨不全症","id":"f4be9bee5c7693c9015c7821672100c0"}],"id":"f4be9bee5c494735015c5d52c4f600a4","title":"骨及软骨病变"},{"enTitle":"Connective Tissue Diseases","diseases":[{"name":"Loeys-Dietz综合征","id":"f4be9bee5c7693c9015c78293a7c00c6"},{"name":"先天性挛缩细长指（趾）","id":"f4be9bee5c7693c9015c7823c2a500c2"},{"name":"先天结缔组织异常Ⅳ型","id":"f4be9bee5c7693c9015c782aba4600c7"},{"name":"瓦登伯格综合症","id":"f4be9bee5c7693c9015c7827381500c5"},{"name":"进行性骨化性肌炎","id":"f4be9bee5c7693c9015c7824e4af00c3"}],"id":"f4be9bee5c494735015c5d53091300a5","title":"结缔组织病变"},{"enTitle":"Blood Diseases","diseases":[{"name":"IPEX 综合症","id":"f4be9bee5c807455015c94e02dbf0190"},{"name":"Wiskott- Aldrich 氏综合症","id":"f4be9bee5c807455015c94ea214b0193"},{"name":"低纤溶酶原症","id":"f4be9bee5c807455015c94baf9d20183"},{"name":"先天性纯红血球再生障碍性贫血","id":"f4be9bee5c807455015c94bfe23b0185"},{"name":"同基因合子蛋白质 C缺乏症","id":"f4be9bee5c807455015c94c934470189"}],"id":"f4be9bee5c494735015c5d53537a00a6","title":"血液疾病"},{"enTitle":"Immune System Diseases","diseases":[{"name":"严重复合型免疫缺乏症","id":"f4be9bee5c807455015c94e4ffc10192"},{"name":"先天性高免疫球蛋白 E综合症","id":"f4be9bee5c807455015c94ee25cc0195"},{"name":"特发性慢性肉芽肿病","id":"f4be9bee5c807455015c94f015480196"},{"name":"白塞氏症","id":"f4be9bee5c807455015c94d25698018c"},{"name":"系统性红斑狼疮","id":"f4be9bee5c807455015c94db45b4018e"}],"id":"f4be9bee5c494735015c5d53a01400a7","title":"免疫疾病"},{"enTitle":"Endocrine System Diseases","diseases":[{"name":"1α-羟化酶缺乏综合症","id":"f4be9bee5c494735015c62c9de26013e"},{"name":"Alsrtöm氏综合症","id":"f4be9bee5c494735015c62baa9f00138"},{"name":"Bardet-Biedl氏综合症","id":"f4be9bee5c494735015c62b8beca0136"},{"name":"Kenny-Caffey氏综合症","id":"f4be9bee5c494735015c62a40203012c"},{"name":"Laron 氏侏儒综合症","id":"f4be9bee5c494735015c62b6f7d50134"}],"id":"f4be9bee5c494735015c5d53e35600a8","title":"内分泌疾病"},{"enTitle":"Congenital Malformation","diseases":[{"name":"Aarskog-Scott氏综合症","id":"f4be9bee5c494735015c6695068b018b"},{"name":"Beckwith Wiedemann 氏综合症","id":"f4be9bee5c494735015c669de57b019b"},{"name":"CFC综合症","id":"f4be9bee5c494735015c66ced0cb01b7"},{"name":"CHARGE 联合征","id":"f4be9bee5c494735015c66da0eaa01c2"},{"name":"Conradi- Hünermann氏综合症","id":"f4be9bee5c494735015c66ae5fbe01ad"}],"id":"f4be9bee5c494735015c5d54258000a9","title":"先天畸形综合征"},{"enTitle":"Chromosome Diseases","diseases":[{"name":"DiGeorge综合症","id":"f4be9bee5c494735015c62f3ce5b014c"},{"name":"Miller-Dieker 综合症","id":"f4be9bee5c494735015c62fccb420150"},{"name":"Prader-Willi氏综合症","id":"f4be9bee5c494735015c62f5f226014d"},{"name":"Von Hippel\u2013Lindau 综合症","id":"f4be9bee5c494735015c6302385c0155"},{"name":"天使综合症","id":"f4be9bee5c494735015c62f241b0014b"}],"id":"f4be9bee5c494735015c5d5464db00aa","title":"染色体异常"},{"enTitle":"Other Unclassified / Unknown Diseases","diseases":[{"name":"Stargardt's氏症","id":"f4be9bee5c494735015c6330749a0164"},{"name":"先天性水痘综合征","id":"f4be9bee5c494735015c631cf46a015e"},{"name":"先天性虹膜缺损","id":"f4be9bee5c494735015c6328e52d0162"},{"name":"克-特二氏综合征","id":"f4be9bee5c494735015c63268e4d0161"},{"name":"发-肝-肠综合症","id":"f4be9bee5c494735015c631021c30159"}],"id":"f4be9bee5c494735015c5d54abc700ab","title":"其他未分类或不明原因"}],"suspension":[{"imgUrl":"images/advertise/5b914f26-a962-4a36-bd27-05d96215bbf0.jpg","linkUrl":"http://www.raredisease.cn/follow!detail?id=f4be9bee5dd05a02015e6c4f4cbd0030"}]}
     * res : {"errMsg":"","status":0}
     */

    private InfBean inf;
    private ResBean res;

    public InfBean getInf() {
        return inf;
    }

    public void setInf(InfBean inf) {
        this.inf = inf;
    }

    public ResBean getRes() {
        return res;
    }

    public void setRes(ResBean res) {
        this.res = res;
    }

    public static class InfBean {
        private List<?> adImage;
        private List<DiseaseBean> disease;
        private List<SuspensionBean> suspension;

        public List<?> getAdImage() {
            return adImage;
        }

        public void setAdImage(List<?> adImage) {
            this.adImage = adImage;
        }

        public List<DiseaseBean> getDisease() {
            return disease;
        }

        public void setDisease(List<DiseaseBean> disease) {
            this.disease = disease;
        }

        public List<SuspensionBean> getSuspension() {
            return suspension;
        }

        public void setSuspension(List<SuspensionBean> suspension) {
            this.suspension = suspension;
        }

        public static class DiseaseBean {
            /**
             * enTitle : Abnormal cell proliferati
             * diseases : [{"name":"vhl综合征","id":"f4be9bee5c494735015c72ebbaf6036b"},{"name":"朗格汉斯组织细胞增多症","id":"f4be9bee5c494735015c72f2e09d036c"},{"name":"淋巴管平滑肌增生症","id":"f4be9bee5c494735015c730167f9036d"},{"name":"视网膜母细胞瘤","id":"f4be9bee5c494735015c7302faed036e"}]
             * id : f4be9bee5af3e6ec015af42fb491000a
             * title : 不正常细胞增生（瘤）
             */

            private String enTitle;
            private String id;
            private String title;
            private List<DiseasesBean> diseases;

            public String getEnTitle() {
                return enTitle;
            }

            public void setEnTitle(String enTitle) {
                this.enTitle = enTitle;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<DiseasesBean> getDiseases() {
                return diseases;
            }

            public void setDiseases(List<DiseasesBean> diseases) {
                this.diseases = diseases;
            }

            public static class DiseasesBean {
                /**
                 * name : vhl综合征
                 * id : f4be9bee5c494735015c72ebbaf6036b
                 */

                private String name;
                private String id;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }
            }
        }

        public static class SuspensionBean {
            /**
             * imgUrl : images/advertise/5b914f26-a962-4a36-bd27-05d96215bbf0.jpg
             * linkUrl : http://www.raredisease.cn/follow!detail?id=f4be9bee5dd05a02015e6c4f4cbd0030
             */

            private String imgUrl;
            private String linkUrl;

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getLinkUrl() {
                return linkUrl;
            }

            public void setLinkUrl(String linkUrl) {
                this.linkUrl = linkUrl;
            }
        }
    }

    public static class ResBean {
        /**
         * errMsg :
         * status : 0
         */

        private String errMsg;
        private int status;

        public String getErrMsg() {
            return errMsg;
        }

        public void setErrMsg(String errMsg) {
            this.errMsg = errMsg;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
