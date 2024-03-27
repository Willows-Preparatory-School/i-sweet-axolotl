package threeD_Test;

import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL43;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.FileHandler;

import static org.lwjgl.opengl.GL11.GL_FLOAT;

/* A shader class to help us manage shaders :3 */
public class Test1Shader
{
    // the program ID
    public int ID;

    // constructor reads and builds the shader
    public Test1Shader(String vertexPath, String fragmentPath)
    {
        // 1. retrieve the vertex/fragment source code from filePath
        String vertexCode;
        String fragmentCode;
        StringBuffer vShaderFile = new StringBuffer();
        StringBuffer fShaderFile = new StringBuffer();
        // ensure ifstream objects can throw exceptions:
        //vShaderFile.exceptions (std::ifstream::failbit | std::ifstream::badbit);
        //fShaderFile.exceptions (std::ifstream::failbit | std::ifstream::badbit);
        try
        {
            // open files
            File vertexFile = new File(vertexPath);
            File fragmentFile = new File(fragmentPath);
            //vShaderFile.open(vertexPath);
            //fShaderFile.open(fragmentPath);
            StringBuilder vShaderStream = new StringBuilder();
            StringBuilder fShaderStream = new StringBuilder();
            // read file's buffer contents into streams
            Scanner vScanner = new Scanner(vertexFile);
            Scanner fScanner = new Scanner(fragmentFile);
            while(vScanner.hasNext())
            {
                vShaderStream.append(vScanner.next());
            }
            while(fScanner.hasNext())
            {
                fShaderStream.append(fScanner.next());
            }
            // close file handlers
            //vShaderFile.close();
            //fShaderFile.close();
            // convert stream into string
            vertexCode   = vShaderStream.toString();
            fragmentCode = fShaderStream.toString();
        }
        catch(FileNotFoundException e) {
            System.out.println("ERROR::SHADER::FILE_NOT_SUCCESSFULLY_READ");
            throw new RuntimeException(e);
        }
        String vShaderCode = vertexCode;
        String fShaderCode = fragmentCode;

        // 2. compile shaders
        int vertex, fragment;
        int[] compileStatus = new int[1];

        // vertex Shader
        vertex = GL43.glCreateShader(GL43.GL_VERTEX_SHADER);
        GL43.glShaderSource(vertex, vShaderCode);
        GL43.glCompileShader(vertex);
        // print compile errors if any
        GL43.glGetShaderiv(vertex, GL43.GL_COMPILE_STATUS, compileStatus);
        if(!(compileStatus[0] == GL43.GL_FALSE))
        {
            String infoLog = GL43.glGetShaderInfoLog(vertex);
            System.out.println("ERROR::SHADER::VERTEX::COMPILATION_FAILED\n" + infoLog);
        };

        // fragment Shader
        fragment = GL43.glCreateShader(GL43.GL_FRAGMENT_SHADER);
        GL43.glShaderSource(fragment, fShaderCode);
        GL43.glCompileShader(fragment);
        // print compile errors if any
        GL43.glGetShaderiv(fragment, GL43.GL_COMPILE_STATUS, compileStatus);
        if(!(compileStatus[0] == GL43.GL_FALSE))
        {
            String infoLog = GL43.glGetShaderInfoLog(fragment);
            System.out.println("ERROR::SHADER::FRAGMENT::COMPILATION_FAILED\n" + infoLog);
        };

        // shader Program
        ID = GL43.glCreateProgram();
        GL43.glAttachShader(ID, vertex);
        GL43.glAttachShader(ID, fragment);
        GL43.glLinkProgram(ID);
        // print linking errors if any
        GL43.glGetProgramiv(ID, GL43.GL_LINK_STATUS, compileStatus);
        if(!(compileStatus[0] == GL43.GL_FALSE))
        {
            String infoLog = GL43.glGetShaderInfoLog(ID);
            System.out.println("ERROR::SHADER::PROGRAM::LINKING_FAILED\n" + infoLog);
        }

        // 3. then set our vertex attributes pointers
        // position attribute
        GL43.glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * 4, 0L);
        GL43.glEnableVertexAttribArray(0);
        // color attribute
        GL43.glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * 4, 3*4L);
        GL43.glEnableVertexAttribArray(1);

        // delete the shaders as they're linked into our program now and no longer necessary
        GL43.glDeleteShader(vertex);
        GL43.glDeleteShader(fragment);
    }

    // use/activate the shader
    public void use()
    {
        GL43.glUseProgram(ID);
    }

    // utility uniform functions
    public void setBool(String name, int value) {
        GL43.glUniform1i(GL43.glGetUniformLocation(ID, name), value);
    }

    public void setInt(String name, int value) {
        GL43.glUniform1i(GL43.glGetUniformLocation(ID, name), value);
    }

    public void setFloat(String name, float value) {
        GL43.glUniform1f(GL43.glGetUniformLocation(ID, name), value);
    }

    public void setVec4f(String name, Vector4f value)
    {
        //System.out.println("Intercepted: (" + value.w + "," + value.x + "," + value.y + "," + value.z + ")");
        GL43.glUniform4f(GL43.glGetUniformLocation(ID, name), value.w, value.x, value.y, value.z);
    }
}
