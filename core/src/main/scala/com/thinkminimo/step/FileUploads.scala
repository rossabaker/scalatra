package com.thinkminimo.step

import java.util.{List => JList}
import javax.servlet.http.{HttpServletResponse, HttpServletRequest}
import org.apache.commons.fileupload.servlet.ServletFileUpload
import org.apache.commons.fileupload.disk.DiskFileItemFactory
import org.apache.commons.fileupload.FileItem
import util.DynamicVariable
import scala.collection.jcl.Conversions._

trait FileUploads extends Handler {
  abstract override def handle(req: HttpServletRequest, res: HttpServletResponse) {
    val items: Seq[FileItem] = parseFileItems(req)
    _fileItems.withValue(fileItemsToMap(items)) {
      super.handle(req, res)
    }
  }

  private def parseFileItems(req: HttpServletRequest): Seq[FileItem] =
    if (ServletFileUpload.isMultipartContent(req)) {
      val factory = new DiskFileItemFactory
      val upload = new ServletFileUpload(factory)
      upload.parseRequest(req).asInstanceOf[JList[FileItem]]
    } else {
      Seq.empty
    }

  private def fileItemsToMap(items: Seq[FileItem]) = items.foldLeft(Map[String, FileItem]()){ (map, item) =>
    map + ((item.getFieldName, item))
  }

  private val _fileItems = new DynamicVariable[Map[String, FileItem]](null)
  protected def fileItems = _fileItems.value 
}