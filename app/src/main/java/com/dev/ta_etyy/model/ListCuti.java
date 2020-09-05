package com.dev.ta_etyy.model;

import java.util.List;

public class ListCuti {

    /**
     * responseCode : 0000
     * responseMessage : Success
     * date : 2020-9-4
     * time : 3:20:1
     * content : [{"id_pengajuan":"95046","npk":"1701082026","tgl_cuti":"2020-08-12","jml_hari":"3","tgl_masuk":"2020-08-14","keterangan":"Sakit"},{"id_pengajuan":"95862","npk":"1701082026","tgl_cuti":"2020-02-12","jml_hari":"3","tgl_masuk":"2020-02-14","keterangan":"Cuti Khusus"},{"id_pengajuan":"18235","npk":"1701082023","tgl_cuti":"2020-02-16","jml_hari":"3","tgl_masuk":"2020-02-18","keterangan":"Cuti Khusus"}]
     */

    private String responseCode;
    private String responseMessage;
    private String date;
    private String time;
    private List<ContentBean> content;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * id_pengajuan : 95046
         * npk : 1701082026
         * tgl_cuti : 2020-08-12
         * jml_hari : 3
         * tgl_masuk : 2020-08-14
         * keterangan : Sakit
         */

        private String id_pengajuan;
        private String npk;
        private String tgl_cuti;
        private String jml_hari;
        private String tgl_masuk;
        private String keterangan;

        public String getId_pengajuan() {
            return id_pengajuan;
        }

        public void setId_pengajuan(String id_pengajuan) {
            this.id_pengajuan = id_pengajuan;
        }

        public String getNpk() {
            return npk;
        }

        public void setNpk(String npk) {
            this.npk = npk;
        }

        public String getTgl_cuti() {
            return tgl_cuti;
        }

        public void setTgl_cuti(String tgl_cuti) {
            this.tgl_cuti = tgl_cuti;
        }

        public String getJml_hari() {
            return jml_hari;
        }

        public void setJml_hari(String jml_hari) {
            this.jml_hari = jml_hari;
        }

        public String getTgl_masuk() {
            return tgl_masuk;
        }

        public void setTgl_masuk(String tgl_masuk) {
            this.tgl_masuk = tgl_masuk;
        }

        public String getKeterangan() {
            return keterangan;
        }

        public void setKeterangan(String keterangan) {
            this.keterangan = keterangan;
        }
    }
}
