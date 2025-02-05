/*
 * Copyright (c) 2001-2018 Territorium Online Srl / TOL GmbH. All Rights
 * Reserved.
 *
 * This file contains Original Code and/or Modifications of Original Code as
 * defined in and that are subject to the Territorium Online License Version
 * 1.0. You may not use this file except in compliance with the License. Please
 * obtain a copy of the License at http://www.tol.info/license/ and read it
 * before using this file.
 *
 * The Original Code and all software distributed under the License are
 * distributed on an 'AS IS' basis, WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESS
 * OR IMPLIED, AND TERRITORIUM ONLINE HEREBY DISCLAIMS ALL SUCH WARRANTIES,
 * INCLUDING WITHOUT LIMITATION, ANY WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE, QUIET ENJOYMENT OR NON-INFRINGEMENT. Please see the
 * License for the specific language governing rights and limitations under the
 * License.
 */

package org.javacc.java;

import org.javacc.Version;
import org.javacc.parser.CodeGeneratorSettings;
import org.javacc.parser.JavaCCErrors;
import org.javacc.parser.JavaCCGlobals;
import org.javacc.parser.JavaCCParserConstants;
import org.javacc.parser.LexGen;
import org.javacc.parser.MetaParseException;
import org.javacc.parser.Options;
import org.javacc.parser.OutputFile;
import org.javacc.parser.RStringLiteral;
import org.javacc.parser.RegExprSpec;
import org.javacc.parser.RegularExpression;
import org.javacc.parser.Token;
import org.javacc.parser.TokenProduction;
import org.javacc.utils.OutputFileGenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@link JavaGlobals} class.
 */
public abstract class JavaGlobals extends JavaCCGlobals implements JavaCCParserConstants {

  /**
   * Constructs an instance of {@link JavaGlobals}.
   */
  private JavaGlobals() {}
  
  public static void genMiscFile(String fileName, String templatePath) throws Error {
    try {
      final File file = new File(Options.getOutputDirectory(), fileName);
      final OutputFile outputFile = new OutputFile(file, Version.majorDotMinor,
          new String[] { /*
                          * cba -- 2013/07/22 -- previously wired to a typo
                          * version of this option -- KEEP_LINE_COL
                          */ Options.USEROPTION__KEEP_LINE_COLUMN });

      if (!outputFile.needToWrite) {
        return;
      }

      final PrintWriter ostr = outputFile.getPrintWriter();

      if (cu_to_insertion_point_1.size() != 0 && cu_to_insertion_point_1.get(0).kind == PACKAGE) {
        for (int i = 1; i < cu_to_insertion_point_1.size(); i++) {
          if (cu_to_insertion_point_1.get(i).kind == SEMICOLON) {
            cline = cu_to_insertion_point_1.get(0).beginLine;
            ccol = cu_to_insertion_point_1.get(0).beginColumn;
            for (int j = 0; j <= i; j++) {
              printToken(cu_to_insertion_point_1.get(j), ostr, true);
            }
            ostr.println("");
            ostr.println("");
            break;
          }
        }
      }

      OutputFileGenerator generator = new OutputFileGenerator(templatePath, Options.getOptions());

      generator.generate(ostr);

      ostr.close();
    } catch (IOException e) {
      System.err.println("Failed to create " + fileName + " " + e);
      JavaCCErrors.semantic_error("Could not open file " + fileName + " for writing.");
      throw new Error();
    }
  }

  public static void gen_Token() {
    try {
      final File file = new File(Options.getOutputDirectory(), "Token.java");
      final OutputFile outputFile =
          new OutputFile(file, Version.majorDotMinor, new String[] { Options.USEROPTION__TOKEN_EXTENDS,
              /*
               * cba -- 2013/07/22 -- previously wired to a typo version of this
               * option -- KEEP_LINE_COL
               */ Options.USEROPTION__KEEP_LINE_COLUMN, Options.USEROPTION__SUPPORT_CLASS_VISIBILITY_PUBLIC });

      if (!outputFile.needToWrite) {
        return;
      }

      final PrintWriter ostr = outputFile.getPrintWriter();

      if (cu_to_insertion_point_1.size() != 0 && cu_to_insertion_point_1.get(0).kind == PACKAGE) {
        for (int i = 1; i < cu_to_insertion_point_1.size(); i++) {
          if (cu_to_insertion_point_1.get(i).kind == SEMICOLON) {
            cline = cu_to_insertion_point_1.get(0).beginLine;
            ccol = cu_to_insertion_point_1.get(0).beginColumn;
            for (int j = 0; j <= i; j++) {
              printToken(cu_to_insertion_point_1.get(j), ostr, true);
            }
            ostr.println("");
            ostr.println("");
            break;
          }
        }
      }

      OutputFileGenerator generator = new OutputFileGenerator("/templates/Token.template", Options.getOptions());

      generator.generate(ostr);

      ostr.close();
    } catch (IOException e) {
      System.err.println("Failed to create Token " + e);
      JavaCCErrors.semantic_error("Could not open file Token.java for writing.");
      throw new Error();
    }
  }


  public static void gen_TokenManager() {
    try {
      final File file = new File(Options.getOutputDirectory(), "TokenManager.java");
      final OutputFile outputFile = new OutputFile(file, Version.majorDotMinor,
          new String[] { Options.USEROPTION__SUPPORT_CLASS_VISIBILITY_PUBLIC });

      if (!outputFile.needToWrite) {
        return;
      }

      final PrintWriter ostr = outputFile.getPrintWriter();

      if (cu_to_insertion_point_1.size() != 0 && cu_to_insertion_point_1.get(0).kind == PACKAGE) {
        for (int i = 1; i < cu_to_insertion_point_1.size(); i++) {
          if (cu_to_insertion_point_1.get(i).kind == SEMICOLON) {
            cline = cu_to_insertion_point_1.get(0).beginLine;
            ccol = cu_to_insertion_point_1.get(0).beginColumn;
            for (int j = 0; j <= i; j++) {
              printToken(cu_to_insertion_point_1.get(j), ostr, true);
            }
            ostr.println("");
            ostr.println("");
            break;
          }
        }
      }

      OutputFileGenerator generator = new OutputFileGenerator("/templates/TokenManager.template", Options.getOptions());

      generator.generate(ostr);

      ostr.close();
    } catch (IOException e) {
      System.err.println("Failed to create TokenManager " + e);
      JavaCCErrors.semantic_error("Could not open file TokenManager.java for writing.");
      throw new Error();
    }
  }

  public static void gen_Constants() throws MetaParseException {
    Token t = null;

    if (JavaCCErrors.get_error_count() != 0)
      throw new MetaParseException();

    // Added this if condition -- 2012/10/17 -- cba


    java.io.PrintWriter ostr = null;
    try {
      ostr = new java.io.PrintWriter(new java.io.BufferedWriter(
          new java.io.FileWriter(new java.io.File(Options.getOutputDirectory(), cu_name + "Constants.java")), 8192));
    } catch (java.io.IOException e) {
      JavaCCErrors.semantic_error("Could not open file " + cu_name + "Constants.java for writing.");
      throw new Error();
    }

    List<String> tn = new ArrayList<String>(toolNames);
    tn.add(toolName);
    
    ostr.println("/* " + getIdString(tn, cu_name + "Constants.java") + " */");

    if (cu_to_insertion_point_1.size() != 0 && cu_to_insertion_point_1.get(0).kind == PACKAGE) {
      for (int i = 1; i < cu_to_insertion_point_1.size(); i++) {
        if (cu_to_insertion_point_1.get(i).kind == SEMICOLON) {
          printTokenSetup(cu_to_insertion_point_1.get(0));
          for (int j = 0; j <= i; j++) {
            t = cu_to_insertion_point_1.get(j);
            printToken(t, ostr, true);
          }
          printTrailingComments(t, ostr, true);
          ostr.println("");
          ostr.println("");
          break;
        }
      }
    }
    ostr.println("");
    ostr.println("/**");
    ostr.println(" * Token literal values and constants.");
    ostr.println(" * Generated by org.javacc.parser.OtherFilesGen#start()");
    ostr.println(" */");

    if (Options.getSupportClassVisibilityPublic()) {
      ostr.print("public ");
    }
    ostr.println("interface " + cu_name + "Constants {");
    ostr.println("");

    RegularExpression re;
    ostr.println("  /** End of File. */");
    ostr.println("  int EOF = 0;");
    for (java.util.Iterator<RegularExpression> it = ordered_named_tokens.iterator(); it.hasNext();) {
      re = it.next();
      ostr.println("  /** RegularExpression Id. */");
      ostr.println("  int " + re.label + " = " + re.ordinal + ";");
    }
    ostr.println("");
    if (!Options.getUserTokenManager() && Options.getBuildTokenManager()) {
      for (int i = 0; i < LexGen.lexStateName.length; i++) {
        ostr.println("  /** Lexical state. */");
        ostr.println("  int " + LexGen.lexStateName[i] + " = " + i + ";");
      }
      ostr.println("");
    }
    ostr.println("  /** Literal token values. */");
    ostr.println("  String[] tokenImage = {");
    ostr.println("    \"<EOF>\",");

    for (java.util.Iterator<TokenProduction> it = rexprlist.iterator(); it.hasNext();) {
      TokenProduction tp = it.next();
      List<RegExprSpec> respecs = tp.respecs;
      for (java.util.Iterator<RegExprSpec> it2 = respecs.iterator(); it2.hasNext();) {
        RegExprSpec res = it2.next();
        re = res.rexp;
        ostr.print("    ");
        if (re instanceof RStringLiteral) {
          ostr.println("\"\\\"" + add_escapes(add_escapes(((RStringLiteral) re).image)) + "\\\"\",");
        } else if (!re.label.equals("")) {
          ostr.println("\"<" + re.label + ">\",");
        } else {
          if (re.tpContext.kind == TokenProduction.TOKEN) {
            JavaCCErrors.warning(re, "Consider giving this non-string token a label for better error reporting.");
          }
          ostr.println("\"<token of kind " + re.ordinal + ">\",");
        }

      }
    }
    ostr.println("  };");
    ostr.println("");
    ostr.println("}");

    ostr.close();
  }

  public static void generateSimple(String template, String outputFileName, String fileHeader, CodeGeneratorSettings settings) throws IOException
  {
    File outputDir = new File((String)settings.get("OUTPUT_DIRECTORY"));
    File outputFile = new File(outputDir, outputFileName);
    final PrintWriter ostr = new PrintWriter(new FileWriter(outputFile));
    OutputFileGenerator generator = new OutputFileGenerator(template, settings);

    ostr.write(JavaGlobals.writePackage());
    
    generator.generate(ostr);
    ostr.close();
  }

  public static String writePackage() {
    StringWriter writer = new StringWriter();
    PrintWriter ostr = new PrintWriter(writer);
    Token t = null;
    if (cu_to_insertion_point_1.size() != 0 && cu_to_insertion_point_1.get(0).kind == PACKAGE) {
      for (int i = 1; i < cu_to_insertion_point_1.size(); i++) {
        if (cu_to_insertion_point_1.get(i).kind == SEMICOLON) {
          printTokenSetup(cu_to_insertion_point_1.get(0));
          for (int j = 0; j <= i; j++) {
            t = cu_to_insertion_point_1.get(j);
            printToken(t, ostr, true);
          }
          printTrailingComments(t, ostr, true);
          ostr.println("");
          ostr.println("");
          break;
        }
      }
    }
    return writer.toString();
  }

  public static String getStatic() {
    return (Options.getStatic() ? JavaGlobals.getStatic() + " " : "");
  }

  public static String getLongType() {
    return "long";
  }

  public static String getBooleanType() {
    return "boolean";
  }

  public static String addUnicodeEscapes(String str) {
    String retval = "";
    char ch;
    for (int i = 0; i < str.length(); i++) {
      ch = str.charAt(i);
      if (ch < 0x20
          || ch > 0x7e /* || ch == '\\' -- cba commented out 20140305 */ ) {
        String s = "0000" + Integer.toString(ch, 16);
        retval += "\\u" + s.substring(s.length() - 4, s.length());
      } else {
        retval += ch;
      }
    }
    return retval;
  }

  public static String getTokenMgrErrorClass() {
    return Options.isLegacyExceptionHandling() ? "TokenMgrError" : "TokenMgrException";
  }

  public static boolean isJavaModern() {
    return Options.getJavaTemplateType().equals(Options.JAVA_TEMPLATE_TYPE_MODERN);
  }

  public static JavaTemplates getTemplates() {
    return isJavaModern() ? JavaGlobals.RESOURCES_JAVA_MODERN : JavaGlobals.RESOURCES_JAVA_CLASSIC;
  }

  public interface JavaTemplates {

    public String getJavaCharStreamTemplateResourceUrl();

    public String getSimpleCharStreamTemplateResourceUrl();

    public String getParseExceptionTemplateResourceUrl();
  }


  private static class JavaTemplatesModern implements JavaTemplates {

    @Override
    public String getJavaCharStreamTemplateResourceUrl() {
      return "/templates/gwt/JavaCharStream.template";
    }

    @Override
    public String getSimpleCharStreamTemplateResourceUrl() {
      return "/templates/gwt/SimpleCharStream.template";
    }

    @Override
    public String getParseExceptionTemplateResourceUrl() {
      return "/templates/gwt/ParseException.template";
    }
  }


  private static class JavaTemplatesClassic implements JavaTemplates {

    @Override
    public String getJavaCharStreamTemplateResourceUrl() {
      return "/templates/JavaCharStream.template";
    }

    @Override
    public String getSimpleCharStreamTemplateResourceUrl() {
      return "/templates/SimpleCharStream.template";
    }

    @Override
    public String getParseExceptionTemplateResourceUrl() {
      return "/templates/ParseException.template";
    }
  }

  private static final JavaTemplates RESOURCES_JAVA_CLASSIC = new JavaTemplatesClassic();
  private static final JavaTemplates RESOURCES_JAVA_MODERN  = new JavaTemplatesModern();
}
