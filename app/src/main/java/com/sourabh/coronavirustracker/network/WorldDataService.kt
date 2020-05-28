package com.sourabh.coronavirustracker.network

import com.sourabh.coronavirustracker.model.WorldDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

private const val COVID_DATA_URL =
    "https://en.wikipedia.org/wiki/Template:COVID-19_pandemic_data#covid19-container"

object WorldDataService {

    suspend fun getData(): List<WorldDataModel> = withContext(Dispatchers.IO) {

        val worldDataList = ArrayList<WorldDataModel>()
        val document = Jsoup.connect(COVID_DATA_URL).maxBodySize(0).get()

        withContext(Dispatchers.Default) {
            getTotalData(worldDataList, document)
            getCountriesData(worldDataList, document)
        }
        worldDataList
    }


    /**
     *  Getting total data separately since the html tags are different
     */
    private fun getTotalData(
        worldDataList: java.util.ArrayList<WorldDataModel>,
        document: Document
    ) {
        val totalString = document.select("#thetable tr.sorttop")
        for (ele in totalString) {
            val totalCases = getCaseData(ele, "th:nth-child(2)")
            val totalDeath = getCaseData(ele, "th:nth-child(3)")
            val totalRecovered = getRecovered(ele, "th:nth-child(4)")

            worldDataList.add(
                WorldDataModel(
                    "World",
                    totalCases,
                    totalDeath,
                    totalRecovered
                )
            )
            break
        }
    }

    /**
     * Countries data
     */
    private fun getCountriesData(
        worldDataList: ArrayList<WorldDataModel>,
        document: Document
    ) {
        val elements = document.select("#thetable > tbody > tr  ")
        for (element in elements) {
            val country = element.select("> th:nth-child(2) > a").text()
            val confirmed = getCaseData(element, "> td:nth-child(3)")
            val deaths = getCaseData(element, "td:nth-child(4)")
            val recovered = getRecovered(element, "td:nth-child(5)")

            val worldData = WorldDataModel(country, confirmed, deaths, recovered)

            if (worldData.country.isNotBlank())
                worldDataList.add(worldData)
        }
    }


    private fun getCaseData(item: Element, selector: String): Int {
        var caseString: String = item.select(selector).text().toString()
        var case = 0
        if (caseString.isNotBlank()) {
            val regex = Regex("[^0-9]")
            caseString = regex.replace(caseString, "")
            if (caseString != "") case = caseString.toInt()
        }
        return case
    }

    /**
     * Getting recovered cases separately since some of them have No data
     */
    private fun getRecovered(item: Element, selector: String): String {
        var caseString: String = item.select(selector).text().toString()
        var case = ""
        if (caseString.isNotBlank() && caseString != "No data") {
            val regex = Regex("[^0-9]")
            caseString = regex.replace(caseString, "")
            if (caseString != "") case = caseString
        }

        if (caseString == "No data") {
            case = "N/A"
        }
        return case
    }
}