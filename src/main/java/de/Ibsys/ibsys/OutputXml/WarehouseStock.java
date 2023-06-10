package de.Ibsys.ibsys.OutputXml;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAttribute;
import java.util.HashMap;
import java.util.List;

public class WarehouseStock {
        private List<Article> articles;


        public List<Article> getArticles() {
            return articles;
        }

        public void setArticles(List<Article> articles) {
            this.articles = articles;
        }
    }

