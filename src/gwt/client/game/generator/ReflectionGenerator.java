//package gwt.client.game.generator;
//
//import java.io.PrintWriter;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.google.gwt.core.ext.BadPropertyValueException;
//import com.google.gwt.core.ext.Generator;
//import com.google.gwt.core.ext.GeneratorContext;
//import com.google.gwt.core.ext.PropertyOracle;
//import com.google.gwt.core.ext.TreeLogger;
//import com.google.gwt.core.ext.UnableToCompleteException;
//import com.google.gwt.core.ext.typeinfo.JClassType;
//import com.google.gwt.core.ext.typeinfo.TypeOracle;
//import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
//import com.google.gwt.user.rebind.SourceWriter;
//
//public class ReflectionGenerator extends Generator
//{    
//    @Override
//    public String generate( TreeLogger logger, GeneratorContext context, String typeName ) throws UnableToCompleteException
//    {
//        TypeOracle oracle = context.getTypeOracle( );
//
//        JClassType someMarkerInterfaceType = oracle.findType( MarkerInterface.class.getName( ) );
//
//        List<JClassType> clazzes = new ArrayList<JClassType>( );
//
//        PropertyOracle propertyOracle = context.getPropertyOracle( );
//
//        for ( JClassType classType : oracle.getTypes( ) )
//        {
//            if ( !classType.equals( someMarkerInterfaceType ) && classType.isAssignableTo( someMarkerInterfaceType ) )
//                clazzes.add( classType );
//        }
//
//        final String genPackageName = "gwt.client.game";
//        final String genClassName = "ReflectionImpl";
//
//        ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory( genPackageName, genClassName );
//        composer.addImplementedInterface( Reflection.class.getCanonicalName( ) );
//        
//        composer.addImport( "gwt.client.game.*" );
//
//        PrintWriter printWriter = context.tryCreate( logger, genPackageName, genClassName );
//
//        if ( printWriter != null )
//        {
//            SourceWriter sourceWriter = composer.createSourceWriter( context, printWriter );
//            sourceWriter.println( "ReflectionImpl( ) {" );
//            sourceWriter.println( "}" );
//
//            printFactoryMethod( clazzes, sourceWriter );
//
//            sourceWriter.commit( logger );
//        }
//        return composer.getCreatedClassName( );
//    }
//
//    private void printFactoryMethod( List<JClassType> clazzes, SourceWriter sourceWriter )
//    {
//        sourceWriter.println( );
//        								//was T
//        sourceWriter.println( "public <T> Object instantiate( Class<T> clazz ) {" );
//
//        for ( JClassType classType : clazzes )
//        {
//            if ( classType.isAbstract( ) )
//                continue;
//
//            sourceWriter.println( );
//            sourceWriter.indent( );
//            sourceWriter.println( "if( clazz.getName().equals(\"" + classType.getName( ) + "\")) {" );
//            sourceWriter.indent( );
//            sourceWriter.println( "return new " + classType.getQualifiedSourceName( ) + "( );" );
//            sourceWriter.outdent( );
//            sourceWriter.println( "}" );
//            sourceWriter.outdent( );
//            sourceWriter.println( );
//        }
//        sourceWriter.println( );
//        sourceWriter.outdent( );
//        sourceWriter.println( "}" );
//        sourceWriter.outdent( );
//        sourceWriter.println( );
//    }
//}

//<generate-with class="gwt.client.game.generator.ReflectionGenerator">
//      <when-type-assignable class="gwt.client.game.generator.Reflection" />
//  </generate-with>
//      