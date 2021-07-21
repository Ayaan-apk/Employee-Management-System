package com.example.cms;

public class pdfmodel {
    String filename,fileUrl;
    int nod,nol,nov;

    public pdfmodel() {
    }

    public pdfmodel(String filename, String fileUrl, int nod, int nol, int nov) {
        this.filename = filename;
        this.fileUrl = fileUrl;
        this.nod = nod;
        this.nol = nol;
        this.nov = nov;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public int getNod() {
        return nod;
    }

    public void setNod(int nod) {
        this.nod = nod;
    }

    public int getNol() {
        return nol;
    }

    public void setNol(int nol) {
        this.nol = nol;
    }

    public int getNov() {
        return nov;
    }

    public void setNov(int nov) {
        this.nov = nov;
    }
}
